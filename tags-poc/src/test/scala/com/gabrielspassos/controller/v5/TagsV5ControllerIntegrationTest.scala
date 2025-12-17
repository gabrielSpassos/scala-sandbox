package com.gabrielspassos.controller.v5

import com.gabrielspassos.repository.TagsRepository
import com.gabrielspassos.repository.v3.TagsV3Repository
import com.gabrielspassos.{Application, BaseIntegrationTest}
import org.json.{JSONArray, JSONObject}
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.{AfterEach, BeforeAll, Test, TestInstance}
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
import org.testcontainers.utility.DockerImageName

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.jdk.OptionConverters.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagsV5ControllerIntegrationTest @Autowired()(private val tagsRepository: TagsRepository) {

  @LocalServerPort
  var randomServerPort: Int = 0

  private val ids = ListBuffer[String]()
  private val client = HttpClient.newHttpClient()

  @AfterEach
  def cleanUp(): Unit = {
    ids.foreach { id =>
      tagsRepository.findById(id).toScala.foreach { entity =>
        tagsRepository.delete(entity)
      }
    }
  }

  @Test
  def shouldCreateEnabledTag(): Unit = {
    val id = UUID.randomUUID().toString
    val url = s"http://localhost:$randomServerPort/v5/tags"
    ids.addOne(id)

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .PUT(HttpRequest.BodyPublishers.noBody())
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = JSONObject(response.body())
    assertEquals(id, responseBody.getString("id"))
    assertEquals("ON", responseBody.getString("value"))
  }

}

object TagsV5ControllerIntegrationTest {

  final class PgContainer(image: DockerImageName)
    extends PostgreSQLContainer[PgContainer](image)

  private val postgres: PgContainer =
    new PgContainer(DockerImageName.parse("postgres:16-alpine"))
      .withDatabaseName("it-test-db")
      .withUsername("sa")
      .withPassword("test")

  postgres.start()

  @DynamicPropertySource
  def datasourceProps(registry: DynamicPropertyRegistry): Unit = {
    registry.add("spring.datasource.url", () => postgres.getJdbcUrl)
    registry.add("spring.datasource.username", () => postgres.getUsername)
    registry.add("spring.datasource.password", () => postgres.getPassword)
  }
}
