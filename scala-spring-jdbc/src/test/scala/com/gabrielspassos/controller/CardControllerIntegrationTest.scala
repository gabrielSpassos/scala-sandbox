package com.gabrielspassos.controller

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.gabrielspassos.Application
import com.gabrielspassos.controller.response.CardResponse
import com.gabrielspassos.dao.CardDAO
import com.gabrielspassos.entity.CardEntity
import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertNotNull, assertNull, assertTrue}
import org.junit.jupiter.api.{Test, TestInstance}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.time.LocalDate
import scala.util.Random

class CardControllerIntegrationTest

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[Application])
)
@EnableAutoConfiguration
@ComponentScan(Array("com.*"))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerIntegrationTests @Autowired()(private val cardDAO: CardDAO) {

  @LocalServerPort
  var randomServerPort: Int = 0

  private val client = HttpClient.newHttpClient()
  private val objectMapper = ObjectMapper()

  @Test
  def shouldGetCardsSuccessfully(): Unit = {
    val url = s"http://localhost:$randomServerPort/cards"
    val response = client.send(
      HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = objectMapper.readValue(response.body(), new TypeReference[java.util.List[CardResponse]]() {})
    assertNotNull(responseBody)
    assertFalse(responseBody.isEmpty)
  }

  @Test
  def shouldGetCardByNumberSuccessfully(): Unit = {
    val card = CardEntity(
      institutionName = "NuBank",
      brand = "MasterCard",
      number = createRandomCardNumber(),
      name = "Test Tester",
      expirationDate = LocalDate.parse("2028-05-30"),
      cvv = Random().between(111, 999).toString
    )
    val savedCard = cardDAO.save(card)

    val url = s"http://localhost:$randomServerPort/cards/${card.number}"
    val response = client.send(
      HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = objectMapper.readValue(response.body(), classOf[CardResponse])
    assertNotNull(responseBody)
    assertEquals(savedCard.id.toString, responseBody.id)
    assertEquals(savedCard.number, responseBody.number)
    assertEquals(savedCard.institutionName, responseBody.institutionName)
    assertEquals(savedCard.brand, responseBody.brand)
    assertEquals(savedCard.name, responseBody.name)
    assertEquals(savedCard.expirationDate.toString, responseBody.expirationDate)
    assertEquals(savedCard.cvv, responseBody.cvv)
  }

  @Test
  def shouldNotFoundCardByNumber(): Unit = {
    val url = s"http://localhost:$randomServerPort/cards/1234567890"
    val response = client.send(
      HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(404, response.statusCode())
    assertNotNull(response.body())
    assertTrue(response.body().isEmpty)
  }

  private def createRandomCardNumber(): String = {
    val randomValue = Random.between(111111111111L, 999999999999L).toString
    "5548" + randomValue
  }
}