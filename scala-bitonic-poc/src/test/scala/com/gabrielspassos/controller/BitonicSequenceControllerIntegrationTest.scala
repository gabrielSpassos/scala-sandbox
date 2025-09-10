package com.gabrielspassos.controller

import com.gabrielspassos.Application
import com.gabrielspassos.DataMock.createGson
import com.gabrielspassos.dao.BitonicSequenceDAO
import com.gabrielspassos.entity.BitonicSequenceEntity
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.{assertEquals, assertNotNull, assertTrue}
import org.junit.jupiter.api.{AfterEach, Test, TestInstance}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.HttpHeaders.{ACCEPT, CONTENT_TYPE}
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.{DynamicPropertyRegistry, DynamicPropertySource}
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.util.UUID
import scala.collection.mutable.ListBuffer

@Testcontainers
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[Application])
)
@EnableAutoConfiguration
@ComponentScan(Array("com.*"))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BitonicSequenceControllerIntegrationTest @Autowired()(private val bitonicSequenceDAO: BitonicSequenceDAO) {

  @LocalServerPort
  var randomServerPort: Int = 0

  private val ids = ListBuffer[UUID]()
  private val client = HttpClient.newHttpClient()
  private val objectMapper = createGson

  @AfterEach
  def cleanUp(): Unit = {
    ids.foreach(id => {
      bitonicSequenceDAO.findById(id) match
        case Some(entity) =>
          bitonicSequenceDAO.delete(entity)
        case None => ()
    })
  }

  @Test
  def shouldCreateSequenceSuccessfully(): Unit = {
    val request = s"""{"size":5, "lowerBoundary":3, "upperBoundary":10}"""
    val url = s"http://localhost:$randomServerPort/v1/bitonic/sequences"

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val jsonBody = JSONObject(response.body())
    assertTrue(jsonBody.has("id"))
    val id = UUID.fromString(jsonBody.getString("id"))
    ids.addOne(id)

    assertEquals("[9,10,9,8,7]", jsonBody.getString("sequence"))
    assertEquals(5, jsonBody.getInt("size"))
    assertEquals(3, jsonBody.getInt("lowerBoundary"))
    assertEquals(10, jsonBody.getInt("upperBoundary"))
  }

  @Test
  def shouldCreateUseDefaultSequence(): Unit = {
    val request = s"""{"size":5, "lowerBoundary":10, "upperBoundary":3}"""
    val url = s"http://localhost:$randomServerPort/v1/bitonic/sequences"

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val jsonBody = JSONObject(response.body())
    assertTrue(jsonBody.has("id"))
    val id = UUID.fromString(jsonBody.getString("id"))
    ids.addOne(id)

    assertEquals("[-1]", jsonBody.getString("sequence"))
    assertEquals(5, jsonBody.getInt("size"))
    assertEquals(10, jsonBody.getInt("lowerBoundary"))
    assertEquals(3, jsonBody.getInt("upperBoundary"))
  }
  
}

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
object BitonicSequenceControllerIntegrationTest {

  val postgresContainer: PostgreSQLContainer[?] = PostgreSQLContainer("postgres:latest")
  postgresContainer.withUsername("sa")
  postgresContainer.withPassword("test")
  postgresContainer.withDatabaseName("it-test-db")
  postgresContainer.withExposedPorts(5432)
  postgresContainer.withInitScripts("schema.sql")

  @DynamicPropertySource
  def configureProperties(registry: DynamicPropertyRegistry): Unit = {
    postgresContainer.start()
    registry.add("spring.datasource.url", () => postgresContainer.getJdbcUrl)
    registry.add("spring.datasource.username", () => postgresContainer.getUsername)
    registry.add("spring.datasource.password", () => postgresContainer.getPassword)
  }
}