package com.gabrielspassos.controller.v5

import com.gabrielspassos.repository.TagsRepository
import com.gabrielspassos.{Application, BaseIntegrationTest}
import org.json.JSONObject
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
class TagsV5ControllerIntegrationTest @Autowired()(private val tagsRepository: TagsRepository) extends BaseIntegrationTest {

  @LocalServerPort
  var randomServerPort: Int = 0

  private val keys = ListBuffer[(String, String)]()
  private val client = HttpClient.newHttpClient()

  @AfterEach
  def cleanUp(): Unit = {
    keys.foreach { (name, entityId) =>
      tagsRepository.findByNameAndEntityId(name, entityId).toScala match {
        case None => // Do nothing
        case Some(tag) => tagsRepository.delete(tag)
      }
    }
  }

  @Test
  def shouldCreateEnabledTag(): Unit = {
    val entityId1 = UUID.randomUUID().toString
    val entityId2 = UUID.randomUUID().toString
    val url = s"http://localhost:$randomServerPort/v5/tags"
    val keyA = s"keyA:$entityId1"
    val keyB = s"keyB:$entityId1"
    val keyC = s"keyC:$entityId2"
    val request =
      s"""
      {
        "$keyA": "ON",
        "$keyB": "OFF",
        "$keyC": "ON"
      }
      """.stripMargin
    keys.addOne(("keyA", entityId1))
    keys.addOne(("keyB", entityId1))
    keys.addOne(("keyC", entityId2))

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
    val jsonKeyA = responseBody.getJSONObject(keyA)
    assertNotNull(jsonKeyA)
    assertEquals("ON", jsonKeyA.getString("value"))
    assertNotNull(jsonKeyA.getString("createdAt"))
    assertNotNull(jsonKeyA.getString("updatedAt"))

    val jsonKeyB = responseBody.getJSONObject(keyB)
    assertNotNull(jsonKeyB)
    assertEquals("OFF", jsonKeyB.getString("value"))
    assertNotNull(jsonKeyB.getString("createdAt"))
    assertNotNull(jsonKeyB.getString("updatedAt"))

    val jsonKeyC = responseBody.getJSONObject(keyC)
    assertNotNull(jsonKeyC)
    assertEquals("ON", jsonKeyC.getString("value"))
    assertNotNull(jsonKeyC.getString("createdAt"))
    assertNotNull(jsonKeyC.getString("updatedAt"))
  }

  @Test
  def shouldUpdateTag(): Unit = {
    val url = s"http://localhost:$randomServerPort/v5/tags"
    val entityId = UUID.randomUUID().toString
    val key = upsertTag("tagName", entityId, "OFF")

    val request = s"""{"$key": "ON"}""".stripMargin
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
    val jsonKey = responseBody.getJSONObject(key)
    assertNotNull(jsonKey)
    assertEquals("ON", jsonKey.getString("value"))
    assertNotNull(jsonKey.getString("createdAt"))
    assertNotNull(jsonKey.getString("updatedAt"))
  }

  @Test
  def getTag(): Unit = {
    val entityId = UUID.randomUUID().toString
    val key = upsertTag("tagName", entityId, "ON")

    val url = s"http://localhost:$randomServerPort/v5/tags?keys=$key"
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
    val jsonKey = responseBody.getJSONObject(key)
    assertNotNull(jsonKey)
    assertEquals("ON", jsonKey.getString("value"))
    assertNotNull(jsonKey.getString("createdAt"))
    assertNotNull(jsonKey.getString("updatedAt"))
  }

  @Test
  def getTags(): Unit = {
    val entityId1 = UUID.randomUUID().toString
    val key1 = upsertTag("tagName1", entityId1, "OFF")
    val entityId2 = UUID.randomUUID().toString
    val key2 = upsertTag("tagName2", entityId2, "ON")
    val key3 = "nonExistentTag:someEntityId"

    val url = s"http://localhost:$randomServerPort/v5/tags?keys=$key1,$key2,$key3"
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

    val jsonKey1 = responseBody.getJSONObject(key1)
    assertNotNull(jsonKey1)
    assertEquals("OFF", jsonKey1.getString("value"))
    assertNotNull(jsonKey1.getString("createdAt"))
    assertNotNull(jsonKey1.getString("updatedAt"))

    val jsonKey2 = responseBody.getJSONObject(key2)
    assertNotNull(jsonKey2)
    assertEquals("ON", jsonKey2.getString("value"))
    assertNotNull(jsonKey2.getString("createdAt"))
    assertNotNull(jsonKey2.getString("updatedAt"))

    assertFalse(responseBody.has(key3))
  }

  @Test
  def shouldNotFoundTag(): Unit = {
    val key = "nonExistentTag:someEntityId"

    val url = s"http://localhost:$randomServerPort/v5/tags?keys=$key"
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
    assertEquals("{}", response.body())
  }

  @Test
  def shouldDeleteTag(): Unit = {
    val key = upsertTag("tagName", UUID.randomUUID().toString, "ON")

    val url = s"http://localhost:$randomServerPort/v5/tags/$key"
    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .DELETE()
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(204, response.statusCode())
  }

  @Test
  def shouldDeleteNonExistingTag(): Unit = {
    val key = "nonExistentTag:someEntityId"

    val url = s"http://localhost:$randomServerPort/v5/tags/$key"
    val response = client.send(
      HttpRequest.newBuilder()
        .uri(URI(url))
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .DELETE()
        .build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(204, response.statusCode())
  }

  private def upsertTag(tagName: String, entityId: String, value: String): String = {
    val request = s"""{"$tagName:$entityId": "$value"}""".stripMargin
    keys.addOne((tagName, entityId))

    val url = s"http://localhost:$randomServerPort/v5/tags"
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
    val tagResponse = responseBody.getJSONObject(s"$tagName:$entityId")
    assertNotNull(tagResponse)
    assertEquals(value, tagResponse.getString("value"))
    assertNotNull(tagResponse.getString("createdAt"))
    assertNotNull(tagResponse.getString("updatedAt"))
    s"$tagName:$entityId"
  }

}
