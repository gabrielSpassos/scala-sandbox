package com.gabrielspassos.controller

import com.gabrielspassos.Application
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[Application])
)
class SearchControllerIntegrationTest {

  @LocalServerPort
  private val randomServerPort: Int = 0

  private val client = HttpClient.newHttpClient()

  @Test
  def shouldTestOpenSearch(): Unit = {
    val baseUrl = s"http://localhost:$randomServerPort/v1/docs"

    val createIndexResponse = client.send(
      HttpRequest.newBuilder()
        .uri(URI.create(s"$baseUrl/index"))
        .POST(HttpRequest.BodyPublishers.noBody())
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(201, createIndexResponse.statusCode())
    assertEquals("documents", createIndexResponse.body())

    val body = """{"id":"1","title":"foo","content":"bar"}"""
    val saveDocumentResponse = client.send(
      HttpRequest.newBuilder()
        .uri(URI.create(s"$baseUrl"))
        .POST(HttpRequest.BodyPublishers.ofString(body))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(201, saveDocumentResponse.statusCode())
    assertEquals("documents", saveDocumentResponse.body())

    val fetchResponse = client.send(
      HttpRequest.newBuilder()
        .uri(URI.create(s"$baseUrl"))
        .GET()
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, saveDocumentResponse.statusCode())
  }

}
