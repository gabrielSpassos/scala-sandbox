package com.gabrielspassos.controller.v2

import com.gabrielspassos.client.CpfGenerator
import com.gabrielspassos.entity.UserEntity
import com.gabrielspassos.repository.{ReportRepository, UserRepository}
import com.gabrielspassos.{Application, BaseIntegrationTest}
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertNotNull, fail}
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
class SimulatorControllerIntegrationTest @Autowired()(private val userRepository: UserRepository) extends BaseIntegrationTest {

  @LocalServerPort
  var randomServerPort: Int = 0

  private val userIds = ListBuffer[UUID]()
  private val externalIds = ListBuffer[(String, String)]()
  private val client = HttpClient.newHttpClient()

  @AfterEach
  def cleanUp(): Unit = {
    userIds.foreach(userId => {
      userRepository.findByUserId(userId).toScala match
        case Some(user) =>
          userRepository.delete(user)
        case None => ()
    })

    externalIds.foreach((externalId1, externalId2) => {
      userRepository.findByExternalId1AndExternalId2(externalId1, externalId2).toScala match
        case Some(user) =>
          userRepository.delete(user)
        case None => ()
    })
  }

  @Test
  def shouldSimulateSuccessUser(): Unit = {
    val externalId1 = createV1User()
    val url = s"http://localhost:$randomServerPort/v2/simulators"

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.noBody())
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(204, response.statusCode())
  }

  @Test
  def shouldSimulateAnMigrateUserSuccessfully(): Unit = {
    val externalId1 = createV1User()
    val url = s"http://localhost:$randomServerPort/v2/simulators?isDryRunMode=false"

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.noBody())
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(204, response.statusCode())
  }

  @Test
  def shouldSimulateFailureUser(): Unit = {
    val externalId1 = createV1User()
    userRepository.findByExternalId1(externalId1).toScala match {
      case None => fail("User not found")
      case Some(user) =>
        val userWithCpf = user.copy(externalId1 = null)
        userRepository.save(userWithCpf)
    }

    val url = s"http://localhost:$randomServerPort/v2/simulators"

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.noBody())
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(204, response.statusCode())
  }

  private def createV1User(): String = {
    val externalId1 = Random().between(1L, Long.MaxValue)
    val externalId2 = Random().between(1L, Long.MaxValue)
    val request =
      s"""
            {
              "externalId1": "$externalId1",
              "externalId2": "$externalId2"
            }
            """
    val url = s"http://localhost:$randomServerPort/v1/users"

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
    assertEquals(externalId1.toString, responseBody.getString("externalId1"))
    assertEquals(externalId2.toString, responseBody.getString("externalId2"))
    externalIds.addOne((externalId1.toString, externalId2.toString))
    externalId1.toString
  }
  
}