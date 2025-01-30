package com.gabrielspassos.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.gabrielspassos.Application
import com.gabrielspassos.DataMock.createBankEntity
import com.gabrielspassos.contracts.v1.response.BankResponse
import com.gabrielspassos.dao.BankDAO
import org.junit.jupiter.api.Assertions.{assertEquals, assertNotNull, assertTrue}
import org.junit.jupiter.api.{Test, TestInstance}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.HttpHeaders.{ACCEPT, CONTENT_TYPE}
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}


@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[Application])
)
@EnableAutoConfiguration
@ComponentScan(Array("com.*"))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankControllerIntegrationTest @Autowired()(private val bankDAO: BankDAO) {
gi
  @LocalServerPort
  var randomServerPort: Int = 0

  private val client = HttpClient.newHttpClient()
  private val objectMapper = ObjectMapper()

  @Test
  def shouldGetCardByNumberSuccessfully(): Unit = {
    val bank = createBankEntity().copy(id = null)
    val savedBank = bankDAO.save(bank)

    val url = s"http://localhost:$randomServerPort/v1/banks/${bank.code}"
    val response = client.send(
      HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = objectMapper.readValue(response.body(), classOf[BankResponse])

    assertNotNull(responseBody)
    assertEquals(savedBank.code, responseBody.getCode)
    assertEquals(savedBank.name, responseBody.getName)
  }

  @Test
  def shouldNotFoundBankByCode(): Unit = {
    val url = s"http://localhost:$randomServerPort/v1/banks/99999"
    val response = client.send(
      HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(404, response.statusCode())
    assertNotNull(response.body())
    assertTrue(response.body().isEmpty)
  }

  @Test
  def shouldCreateBank(): Unit = {
    val request =
      """
      {
        "code": "700",
        "name": "Fake Bank"
      }
      """
    val url = s"http://localhost:$randomServerPort/v1/banks"

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(201, response.statusCode())
    assertNotNull(response.body())
    assertEquals("{\"code\":\"700\",\"name\":\"Fake Bank\"}", response.body())
  }

  @Test
  def shouldFailToCreateBank(): Unit = {
    val request =
      """
        {
          "code": "999",
          "name": "Fake Bank 2"
        }
        """
    val url = s"http://localhost:$randomServerPort/v1/banks"

    val response1 = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    val response2 = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(201, response1.statusCode())
    assertNotNull(response1.body())
    assertEquals("{\"code\":\"999\",\"name\":\"Fake Bank 2\"}", response1.body())

    assertEquals(500, response2.statusCode())
    assertNotNull(response2.body())
  }

}