package com.gabrielspassos.controller.v2

import com.gabrielspassos.client.CpfGenerator
import com.gabrielspassos.repository.UserRepository
import com.gabrielspassos.{Application, BaseIntegrationTest}
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertNotNull}
import org.junit.jupiter.api.{AfterEach, Test, TestInstance}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.HttpHeaders.{ACCEPT, CONTENT_TYPE}
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.jdk.OptionConverters.*
import scala.util.Random

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[Application])
)
@EnableAutoConfiguration
@ComponentScan(Array("com.*"))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerV2IntegrationTest @Autowired()(private val userRepository: UserRepository) extends BaseIntegrationTest {

  @LocalServerPort
  var randomServerPort: Int = 0

  private val userIds = ListBuffer[UUID]()
  private val client = HttpClient.newHttpClient()

  @AfterEach
  def cleanUp(): Unit = {
    userIds.foreach(userId => {
      userRepository.findByUserId(userId).toScala match
        case Some(user) =>
          userRepository.delete(user)
        case None => ()
    })
  }

  @Test
  def shouldCreateUser(): Unit = {
    val cpf = CpfGenerator.generate()
    val request = s"""{"cpf": "$cpf"}"""
    val url = s"http://localhost:$randomServerPort/v2/users"

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

    val responseBody = JSONObject(response.body())
    assertFalse(responseBody.isNull("userId"))
    val userId = UUID.fromString(responseBody.getString("userId"))
    userIds.addOne(userId)
  }

  @Test
  def shouldFailToCreateSameUserAgain(): Unit = {
    val cpf = CpfGenerator.generate()
    val request = s"""{"cpf": "$cpf"}"""
    val url = s"http://localhost:$randomServerPort/v2/users"

    val response1 = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    val responseBody = JSONObject(response1.body())
    assertFalse(responseBody.isNull("userId"))
    val userId = UUID.fromString(responseBody.getString("userId"))
    userIds.addOne(userId)

    val response2 = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(400, response2.statusCode())
    assertNotNull(response2.body())
  }

}
