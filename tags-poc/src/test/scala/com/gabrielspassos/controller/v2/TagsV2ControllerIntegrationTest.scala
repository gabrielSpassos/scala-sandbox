package com.gabrielspassos.controller.v2

import com.gabrielspassos.repository.v2.TagsV2Repository
import com.gabrielspassos.{Application, BaseIntegrationTest}
import org.json.{JSONArray, JSONObject}
import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertNotNull, assertTrue, fail}
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
class TagsV2ControllerIntegrationTest @Autowired()(private val tagsV2Repository: TagsV2Repository) extends BaseIntegrationTest {

  @LocalServerPort
  var randomServerPort: Int = 0

  private val ids = ListBuffer[String]()
  private val client = HttpClient.newHttpClient()

  @AfterEach
  def cleanUp(): Unit = {
    ids.foreach { id =>
      tagsV2Repository.findById(id).toScala.foreach { entity =>
        tagsV2Repository.delete(entity)
      }
    }
  }

  @Test
  def shouldCreateEnabledTag(): Unit = {
    val id = UUID.randomUUID().toString
    val request =s"""{"isEnabled": true}"""
    val url = s"http://localhost:$randomServerPort/v2/tags/$id"
    ids.addOne(id)

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .PUT(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = JSONObject(response.body())
    assertEquals(id, responseBody.getString("id"))
    assertFalse(responseBody.isNull("enabledAt"))
    assertTrue(responseBody.isNull("disabledAt"))
  }

  @Test
  def shouldCreateDisabledTag(): Unit = {
    val id = UUID.randomUUID().toString
    val request = s"""{"isEnabled": false}"""
    val url = s"http://localhost:$randomServerPort/v2/tags/$id"
    ids.addOne(id)

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .PUT(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = JSONObject(response.body())
    assertEquals(id, responseBody.getString("id"))
    assertTrue(responseBody.isNull("enabledAt"))
    assertFalse(responseBody.isNull("disabledAt"))
  }

  @Test
  def shouldUpdateTag(): Unit = {
    val id = createTag(isEnabled = false)

    val url = s"http://localhost:$randomServerPort/v2/tags/$id"
    val request = s"""{"isEnabled": true}"""
    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .PUT(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = JSONObject(response.body())
    assertEquals(id, responseBody.getString("id"))
    assertFalse(responseBody.isNull("enabledAt"))
    assertTrue(responseBody.isNull("disabledAt"))
  }

  @Test
  def shouldGetTag(): Unit = {
    val id = createTag(isEnabled = true)

    val url = s"http://localhost:$randomServerPort/v2/tags/$id"
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
    assertFalse(responseBody.isNull("enabledAt"))
    assertTrue(responseBody.isNull("disabledAt"))
  }

  @Test
  def shouldNotFoundTagOnFindOperation(): Unit = {
    val id = UUID.randomUUID().toString

    val url = s"http://localhost:$randomServerPort/v2/tags/$id"
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
    val id1 = createTag(isEnabled = true)
    val id2 = createTag(isEnabled = false)

    val url = s"http://localhost:$randomServerPort/v2/tags?ids=$id1,$id2"
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
      case `id1` =>
        assertFalse(object1.isNull("enabledAt"))
        assertTrue(object1.isNull("disabledAt"))
      case `id2` =>
        assertTrue(object1.isNull("enabledAt"))
        assertFalse(object1.isNull("disabledAt"))
      case _ => fail("unexpected id")
    }

    object2.getString("id") match {
      case `id1` =>
        assertFalse(object2.isNull("enabledAt"))
        assertTrue(object2.isNull("disabledAt"))
      case `id2` =>
        assertTrue(object2.isNull("enabledAt"))
        assertFalse(object2.isNull("disabledAt"))
      case _ => fail("unexpected id")
    }
  }

  @Test
  def shouldGetEmptyTags(): Unit = {
    val id1 = UUID.randomUUID()
    val id2 = UUID.randomUUID()

    val url = s"http://localhost:$randomServerPort/v2/tags?ids=$id1,$id2"
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
    val id = createTag(isEnabled = true)

    val url = s"http://localhost:$randomServerPort/v2/tags/$id"
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
    assertFalse(responseBody.isNull("enabledAt"))
    assertTrue(responseBody.isNull("disabledAt"))
  }

  @Test
  def shouldNotFoundTagOnDeleteOperation(): Unit = {
    val id = UUID.randomUUID().toString

    val url = s"http://localhost:$randomServerPort/v2/tags/$id"
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


  private def createTag(isEnabled: Boolean = true): String = {
    val id = UUID.randomUUID().toString
    val request = s"""{"isEnabled": $isEnabled}"""
    val url = s"http://localhost:$randomServerPort/v2/tags/$id"
    ids.addOne(id)

    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .PUT(HttpRequest.BodyPublishers.ofString(request))
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())
    val responseBody = JSONObject(response.body())
    assertEquals(id, responseBody.getString("id"))
    id
  }

}