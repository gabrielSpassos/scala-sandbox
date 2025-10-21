package com.gabrielspassos.controller.v1

import com.gabrielspassos.entity.UserEntity
import com.gabrielspassos.repository.{ReportRepository, UserRepository}
import com.gabrielspassos.{Application, BaseIntegrationTest}
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.{assertEquals, assertNotNull, fail}
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
class ReportControllerIntegrationTest @Autowired()(private val userRepository: UserRepository,
                                                   private val reportRepository: ReportRepository) extends BaseIntegrationTest {

  @LocalServerPort
  var randomServerPort: Int = 0

  private val externalIds = ListBuffer[(String, String)]()
  private val externalId1s = ListBuffer[String]()
  private val client = HttpClient.newHttpClient()

  @AfterEach
  def cleanUp(): Unit = {
    externalIds.foreach((externalId1, externalId2) => {
      userRepository.findByExternalId1AndExternalId2(externalId1, externalId2).toScala match
        case Some(user) =>
          userRepository.delete(user)
        case None => ()
    })
    externalId1s.foreach(externalId1 => {
      reportRepository.findByExternalId1(externalId1).toScala match
        case Some(report) =>
          reportRepository.delete(report)
        case None => ()
    })
  }

  @Test
  def shouldCreateReport(): Unit = {
    val externalId1 = createUser()
    val url = s"http://localhost:$randomServerPort/v1/reports/$externalId1"

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.noBody())
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = JSONObject(response.body())
    assertEquals(externalId1, responseBody.getString("externalId1"))
    assertNotNull(responseBody.getString("content"))
    externalId1s.addOne(externalId1)
  }

  @Test
  def shouldFailToCreateReportWithNotFoundUser(): Unit = {
    val externalId1 = UUID.randomUUID().toString
    val url = s"http://localhost:$randomServerPort/v1/reports/$externalId1"

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.noBody())
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(404, response.statusCode())
    assertNotNull(response.body())
  }

  @Test
  def shouldFailToCreateReportWithInactiveUser(): Unit = {
    val externalId1 = createUser()
    val user = userRepository.findByExternalId1(externalId1).toScala match {
      case None => fail(s"Should find user by externalId1=$externalId1")
      case Some(user) => user
    }
    userRepository.save(user.copy(status = UserEntity.inactiveStatus))
    
    val url = s"http://localhost:$randomServerPort/v1/reports/$externalId1"

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.noBody())
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(400, response.statusCode())
    assertNotNull(response.body())
  }
  
  private def createUser(): String = {
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