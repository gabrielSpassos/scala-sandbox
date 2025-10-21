package com.gabrielspassos.controller

import com.gabrielspassos.Application
import com.gabrielspassos.DataMock.{createCardEntity, createGson}
import com.gabrielspassos.entity.UserEntity
import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertNotNull, assertTrue}
import org.junit.jupiter.api.{AfterEach, Test, TestInstance}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import scala.collection.mutable.ListBuffer

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[Application])
)
@EnableAutoConfiguration
@ComponentScan(Array("com.*"))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerIntegrationTest @Autowired()(private val cardDAO: CardDAO) {

  @LocalServerPort
  var randomServerPort: Int = 0

  private val cardEntities = ListBuffer[UserEntity]()
  private val client = HttpClient.newHttpClient()
  private val objectMapper = createGson

  @AfterEach
  def cleanUp(): Unit = {
    cardEntities.foreach(card => cardDAO.delete(card))
  }

  @Test
  def shouldGetCardsSuccessfully(): Unit = {
    val url = s"http://localhost:$randomServerPort/v1/cards"
    val response = client.send(
      HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())


    val responseBody = objectMapper.fromJson(response.body(), new TypeToken[java.util.List[CardResponse]]() {})
    assertNotNull(responseBody)
    assertFalse(responseBody.isEmpty)
    assertEquals(3, responseBody.size())
  }

  @Test
  def shouldGetCardByNumberSuccessfully(): Unit = {
    val card = createCardEntity().copy(id = null)
    val savedCard = cardDAO.save(card)
    cardEntities.addOne(savedCard)

    val url = s"http://localhost:$randomServerPort/v1/cards/${card.number}"
    val response = client.send(
      HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = objectMapper.fromJson(response.body(), classOf[CardResponse])
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
    val url = s"http://localhost:$randomServerPort/v1/cards/1234567890"
    val response = client.send(
      HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(404, response.statusCode())
    assertNotNull(response.body())
    assertTrue(response.body().isEmpty)
  }

}