package com.gabrielspassos.controller.v3

import com.gabrielspassos.repository.v3.TagsV3Repository
import com.gabrielspassos.{Application, BaseIntegrationTest}
import org.json.{JSONArray, JSONObject}
import org.junit.jupiter.api.Assertions.*
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

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[Application])
)
@EnableAutoConfiguration
@ComponentScan(Array("com.*"))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagsV3ControllerIntegrationTest @Autowired()(private val tagsV3Repository: TagsV3Repository) extends BaseIntegrationTest {

  @LocalServerPort
  var randomServerPort: Int = 0

  private val ids = ListBuffer[String]()
  private val client = HttpClient.newHttpClient()

  @AfterEach
  def cleanUp(): Unit = {
    ids.foreach { id =>
      tagsV3Repository.findById(id).toScala.foreach { entity =>
        tagsV3Repository.delete(entity)
      }
    }
  }

  @Test
  def shouldCreateEnabledTag(): Unit = {
    val id = UUID.randomUUID().toString
    val url = s"http://localhost:$randomServerPort/v3/tags/$id/ON"
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

  @Test
  def shouldCreateDisabledTag(): Unit = {
    val id = UUID.randomUUID().toString
    val url = s"http://localhost:$randomServerPort/v3/tags/$id/off"
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
    assertEquals("OFF", responseBody.getString("value"))
  }

  @Test
  def shouldUpdateTag(): Unit = {
    val id = createTag(enabledValue = "OFF")

    val url = s"http://localhost:$randomServerPort/v3/tags/$id/on"
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

  @Test
  def shouldFailToCreateTagWithInvalidValue(): Unit = {
    val id = UUID.randomUUID().toString
    val url = s"http://localhost:$randomServerPort/v3/tags/$id/FOO"
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

    assertEquals(400, response.statusCode())
    assertNotNull(response.body())

    val responseBody = JSONObject(response.body())
    assertNotNull(responseBody.getString("code"))
    assertNotNull(responseBody.getString("message"))
  }

  @Test
  def shouldGetTag(): Unit = {
    val id = createTag(enabledValue = "ON")

    val url = s"http://localhost:$randomServerPort/v3/tags/$id"
    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .GET()
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = JSONObject(response.body())
    assertEquals(id, responseBody.getString("id"))
    assertEquals("ON", responseBody.getString("value"))
  }

  @Test
  def shouldNotFoundTagOnFindOperation(): Unit = {
    val id = UUID.randomUUID().toString

    val url = s"http://localhost:$randomServerPort/v3/tags/$id"
    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .GET()
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(404, response.statusCode())
    assertNotNull(response.body())

    val responseBody = JSONObject(response.body())
    assertNotNull(responseBody.getString("code"))
    assertNotNull(responseBody.getString("message"))
  }

  @Test
  def shouldGetTags(): Unit = {
    val id1 = createTag(enabledValue = "ON")
    val id2 = createTag(enabledValue = "OFF")

    val url = s"http://localhost:$randomServerPort/v3/tags?ids=$id1,$id2"
    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .GET()
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = JSONArray(response.body())
    assertEquals(2, responseBody.length())
    val object1 = responseBody.getJSONObject(0)
    val object2 = responseBody.getJSONObject(1)

    object1.getString("id") match {
      case `id1` => assertEquals("ON", object1.getString("value"))
      case `id2` => assertEquals("OFF", object1.getString("value"))
      case _ => fail("unexpected id")
    }

    object2.getString("id") match {
      case `id1` => assertEquals("ON", object2.getString("value"))
      case `id2` => assertEquals("OFF", object2.getString("value"))
      case _ => fail("unexpected id")
    }
  }

  @Test
  def shouldGetEmptyTags(): Unit = {
    val id1 = UUID.randomUUID()
    val id2 = UUID.randomUUID()

    val url = s"http://localhost:$randomServerPort/v3/tags?ids=$id1,$id2"
    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .GET()
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = JSONArray(response.body())
    assertEquals(0, responseBody.length())
  }

  @Test
  def shouldDeleteTag(): Unit = {
    val id = createTag(enabledValue = "ON")

    val url = s"http://localhost:$randomServerPort/v3/tags/$id"
    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .DELETE()
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = JSONObject(response.body())
    assertEquals(id, responseBody.getString("id"))
    assertEquals("ON", responseBody.getString("value"))
  }

  @Test
  def shouldNotFoundTagOnDeleteOperation(): Unit = {
    val id = UUID.randomUUID().toString

    val url = s"http://localhost:$randomServerPort/v3/tags/$id"
    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .DELETE()
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(404, response.statusCode())
    assertNotNull(response.body())

    val responseBody = JSONObject(response.body())
    assertNotNull(responseBody.getString("code"))
    assertNotNull(responseBody.getString("message"))
  }


  private def createTag(enabledValue: String = "ON"): String = {
    val id = UUID.randomUUID().toString
    val url = s"http://localhost:$randomServerPort/v3/tags/$id/$enabledValue"
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
    assertEquals(enabledValue, responseBody.getString("value"))
    id
  }

}