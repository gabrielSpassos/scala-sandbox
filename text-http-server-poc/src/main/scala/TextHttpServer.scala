
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directives, Route}
import logger.Logger.info
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object TextHttpServer {

  case class WebPage(content: String)

  implicit val system: ActorSystem[_] = ActorSystem(Behaviors.empty, "text-http-server")
  implicit val executionContext: ExecutionContext = system.executionContext

  implicit val orderFormat: RootJsonFormat[WebPage] = jsonFormat1(WebPage.apply)

  private val webPagesMap = collection.mutable.Map[String, WebPage]()

  private def createWebPage(key: String): Either[String, WebPage] = {
    if (key.isBlank || key.isEmpty) {
      return Left("Invalid key")
    }

    if (webPagesMap.contains(key)) {
      Right(webPagesMap(key))
    } else {
      webPagesMap += (key -> WebPage(""))
      Right(webPagesMap(key))
    }
  }

  private def getWebPage(key: String): Option[WebPage] = {
    webPagesMap.get(key)
  }

  private def updateWebPage(key: String, webPage: WebPage): Option[Either[String, WebPage]] = {
    def wrapUpdate(key: String, webPage: WebPage): WebPage = {
      webPagesMap(key) = webPage
      webPage
    }

    for {
      _ <- getWebPage(key)
      updatedPage = wrapUpdate(key, webPage)
    } yield {
      updatedPage match {
        case page: WebPage => Right(page)
        case _ => Left("Invalid key")
      }
    }

  }

  def main(args: Array[String]): Unit = {
    val route: Route =
      Directives.concat(
        Directives.post {
          pathPrefix("pages" / RemainingPath) { pageKey =>
            val maybePage = createWebPage(pageKey.toString())

            maybePage match {
              case Right(page) => complete(page)
              case Left(message) => complete(StatusCodes.BadRequest, message)
            }
          }
        },
        Directives.get {
          pathPrefix("pages" / RemainingPath) { pageKey =>
            val maybePage: Option[WebPage] = getWebPage(pageKey.toString())

            maybePage match {
              case Some(page) => complete(page)
              case None => complete(StatusCodes.NotFound)
            }
          }
        },
        Directives.put {
          path("pages" / RemainingPath) { pageKey =>
            entity(as[WebPage]) { webPage =>
              val maybePage = updateWebPage(pageKey.toString(), webPage)

              maybePage match {
                case Some(Right(page)) => complete(page)
                case Some(Left(message)) => complete(StatusCodes.BadRequest, message)
                case None => complete(StatusCodes.NotFound)
              }
            }
          }
        }
      )

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)
    info(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
