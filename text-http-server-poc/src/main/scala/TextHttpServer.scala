
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

  val webPagesMap = collection.mutable.Map[String, WebPage]()

  def createWebPage(key: String): WebPage = {
    if (webPagesMap.contains(key)) {
      webPagesMap(key)
    } else {
      webPagesMap += (key -> WebPage(""))
      webPagesMap(key)
    }
  }

  def getWebPage(key: String): Option[WebPage] = {
    webPagesMap.get(key)
  }

  def updateWebPage(key: String, webPage: WebPage): WebPage = {
    webPagesMap(key) = webPage
    webPage
  }

  def main(args: Array[String]): Unit = {
    val route: Route =
      Directives.concat(
        Directives.post {
          pathPrefix("pages" / RemainingPath) { pageKey =>
            val webPage: WebPage = createWebPage(pageKey.toString())

            complete(webPage)
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
              val updatedPage: WebPage = updateWebPage(pageKey.toString(), webPage)

              complete(updatedPage)
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
