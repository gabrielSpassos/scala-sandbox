package com.gabrielspassos.controller.v1

import com.gabrielspassos.ScalaApplication
import org.junit.jupiter.api.Assertions.{assertEquals, assertNotNull}
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import scala.io.Source

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[ScalaApplication])
)
class ProductControllerIntegrationTest {

  @Test
  def shouldCreateProduct(): Unit = {
    val httpClient = HttpClient.newHttpClient()
    val requestBody = Source.fromResource("create-product.json")
    val request = HttpRequest.newBuilder()
      .uri(URI.create("http://localhost:8080/v1/products"))
      .POST(BodyPublishers.ofString("product"))
      .build()
    val response = httpClient.send(
      request,
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(null != response.body())
  }

}
