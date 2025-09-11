package com.gabrielspassos.controller

import com.gabrielspassos.Application
import com.gabrielspassos.DataMock.createGson
import com.gabrielspassos.dao.{BitonicSequenceCacheDAO, BitonicSequenceDAO}
import com.gabrielspassos.entity.BitonicSequenceEntity
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.{assertEquals, assertNotEquals, assertNotNull, assertTrue}
import org.junit.jupiter.api.{AfterEach, Test, TestInstance}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.HttpHeaders.{ACCEPT, CONTENT_TYPE}
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.{DynamicPropertyRegistry, DynamicPropertySource}
import org.testcontainers.containers.{GenericContainer, PostgreSQLContainer}
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
class BitonicSequenceControllerIntegrationTest @Autowired()(private val bitonicSequenceDAO: BitonicSequenceDAO,
                                                            private val bitonicSequenceCacheDAO: BitonicSequenceCacheDAO) {

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
          bitonicSequenceCacheDAO
            .findBySizeAndLowerBoundaryAndUpperBoundary(entity.size, entity.lowerBoundary, entity.upperBoundary) match
            case Some(cacheEntity) => bitonicSequenceCacheDAO.delete(cacheEntity)
            case None => ()

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
  def shouldFetchSequenceFromCacheSuccessfully(): Unit = {
    val request = s"""{"size":5, "lowerBoundary":3, "upperBoundary":10}"""
    val url = s"http://localhost:$randomServerPort/v1/bitonic/sequences"

    val response1 = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response1.statusCode())
    assertNotNull(response1.body())

    val jsonBody1 = JSONObject(response1.body())
    assertTrue(jsonBody1.has("id"))
    val id1 = UUID.fromString(jsonBody1.getString("id"))
    ids.addOne(id1)

    assertEquals("[9,10,9,8,7]", jsonBody1.getString("sequence"))
    assertEquals(5, jsonBody1.getInt("size"))
    assertEquals(3, jsonBody1.getInt("lowerBoundary"))
    assertEquals(10, jsonBody1.getInt("upperBoundary"))

    val response2 = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .POST(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response2.statusCode())
    assertNotNull(response2.body())

    val jsonBody2 = JSONObject(response2.body())
    assertTrue(jsonBody2.has("id"))
    val id2 = UUID.fromString(jsonBody2.getString("id"))

    assertNotEquals(id1, id2)
    assertEquals("[9,10,9,8,7]", jsonBody2.getString("sequence"))
    assertEquals(5, jsonBody2.getInt("size"))
    assertEquals(3, jsonBody2.getInt("lowerBoundary"))
    assertEquals(10, jsonBody2.getInt("upperBoundary"))
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

  val redisContainer: GenericContainer[?] = new GenericContainer("redis:latest").withExposedPorts(6379)

  @DynamicPropertySource
  def configureProperties(registry: DynamicPropertyRegistry): Unit = {
    postgresContainer.start()
    redisContainer.start()

    registry.add("spring.data.redis.host", () => redisContainer.getHost)
    registry.add("spring.data.redis.port", () => redisContainer.getFirstMappedPort)

    registry.add("spring.datasource.url", () => postgresContainer.getJdbcUrl)
    registry.add("spring.datasource.username", () => postgresContainer.getUsername)
    registry.add("spring.datasource.password", () => postgresContainer.getPassword)
  }
}