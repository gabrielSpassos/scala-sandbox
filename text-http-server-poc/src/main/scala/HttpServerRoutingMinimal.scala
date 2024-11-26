import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, path}
import akka.http.scaladsl.server.{Directives, Route}
import logger.Logger.info

import scala.io.StdIn


object HttpServerRoutingMinimal {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty, "simple-http-server")
    implicit val executionContext = system.executionContext

    val routes: Route = path("hello") {
      Directives.get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Hello Text HTTP Server!</h1>"))
      }
    }

    val bindingsFuture = Http().newServerAt("localhost", 8080).bind(routes)

    info("Server now online. Please navigate to http://localhost:8080/hello\nPress RETURN to stop...")
    StdIn.readLine() // let server run until press return
    bindingsFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate())
  }
}
