import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import logger.Logger.{error, info}

import scala.concurrent.Future
import scala.util.{Failure, Success}

object HttpClientSingleRequest {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty, "single-request-http-server")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://google.com"))

    responseFuture
      .onComplete {
        case Success(res) => info("Success response %s", res)
        case Failure(_)   => error("something wrong")
      }
  }

}
