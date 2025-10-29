import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import scala.util.Random

object Feeder {

  def main(args: Array[String]): Unit = {
    val N = if (args.nonEmpty) args(0).toInt else 1000 // Default 1000 runs
    val baseUrl = "http://localhost:8080"
    val client = HttpClient.newHttpClient()

    (1 to N).foreach { i =>
      val externalId1 = Random().between(1L, Long.MaxValue)
      val externalId2 = Random().between(1L, Long.MaxValue)

      println(s"\n=== Run #$i ===")
      println(s"externalId1: $externalId1")
      println(s"externalId2: $externalId2")

      val userPayload =
        s"""{
           |  "externalId1": "$externalId1",
           |  "externalId2": "$externalId2"
           |}""".stripMargin

      try {
        val userRequest = HttpRequest.newBuilder()
          .uri(URI.create(s"$baseUrl/v1/users"))
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(userPayload))
          .build()

        val userResponse = client.send(userRequest, HttpResponse.BodyHandlers.ofString())
        println(s"Created user httpStatus: [${userResponse.statusCode()}]")

        val reportRequest = HttpRequest.newBuilder()
          .uri(URI.create(s"$baseUrl/v1/reports/$externalId1"))
          .POST(HttpRequest.BodyPublishers.noBody())
          .build()

        val reportResponse = client.send(reportRequest, HttpResponse.BodyHandlers.ofString())
        println(s"Report created httpStatus: [${reportResponse.statusCode()}]")

      } catch {
        case e: Exception =>
          println(s"❌ Error during run #$i: ${e.getMessage}")
      }
    }

    println(s"\n✅ Completed $N runs.")
  }
}
