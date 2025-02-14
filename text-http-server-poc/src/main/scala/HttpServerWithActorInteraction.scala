import akka.actor.typed.scaladsl.AskPattern.{Askable, _}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives._
import akka.util.Timeout
import logger.Logger.info
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn

object HttpServerWithActorInteraction {

  object Auction {
    sealed trait Message

    case class Bid(userId: String, offer: Int) extends Message
    case class GetBids(replayTo: ActorRef[Bids]) extends Message
    case class Bids(bids: List[Bid])

    def apply: Behaviors.Receive[Message] = apply(List.empty)

    def apply(bids: List[Bid]): Behaviors.Receive[Message] = Behaviors.receive {
      case (ctx, bid @ Bid(userId, offer)) =>
        info(s"Bid complete: $userId, $offer")
        apply(bids :+ bid)
      case (_, GetBids(replayTo)) =>
        replayTo ! Bids(bids)
        Behaviors.same
    }
  }

  // these are from spray-json
  implicit val bidFormat: RootJsonFormat[Auction.Bid] = jsonFormat2(Auction.Bid.apply)
  implicit val bidsFormat: RootJsonFormat[Auction.Bids] = jsonFormat1(Auction.Bids.apply)

  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem[Auction.Message] = ActorSystem(Auction.apply, "auction")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContext = system.executionContext

    val auction: ActorRef[Auction.Message] = system
    import Auction._

    val route =
      path("auction") {
        Directives.concat(
          put {
            parameters("bid".as[Int], "user") { (bid, user) =>
              // place a bid, fire-and-forget
              auction ! Bid(user, bid)
              complete(StatusCodes.Accepted, "bid placed")
            }
          },
          get {
            implicit val timeout: Timeout = 5.seconds

            // query the actor for the current auction state
            val bids: Future[Bids] = auction.ask(GetBids(_))
            complete(bids)
          }
        )
      }

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)
    info(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
