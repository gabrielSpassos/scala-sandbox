package com.gabrielspassos.controller

import com.gabrielspassos.Application
import com.gabrielspassos.DataMock.createGson
import com.gabrielspassos.dto.CountryDTO
import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertNotNull}
import org.junit.jupiter.api.{Test, TestInstance}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[Application])
)
@EnableAutoConfiguration
@ComponentScan(Array("com.*"))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CountryControllerIntegrationTest @Autowired() {

  @LocalServerPort
  var randomServerPort: Int = 0

  private val client = HttpClient.newHttpClient()
  private val objectMapper = createGson

  @Test
  def shouldGetCountriesSuccessfully(): Unit = {
    val url = s"http://localhost:$randomServerPort/v1/countries"
    val response = client.send(
      HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())


    val responseBody = objectMapper.fromJson(response.body(), new TypeToken[java.util.List[CountryDTO]]() {})
    assertNotNull(responseBody)
    assertFalse(responseBody.isEmpty)
    assertEquals(250, responseBody.size())
  }

  @Test
  def shouldGetBrazilCountrySuccessfully(): Unit = {
    val url = s"http://localhost:$randomServerPort/v1/countries/brazil"
    val response = client.send(
      HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())


    val responseBody = objectMapper.fromJson(response.body(), new TypeToken[List[CountryDTO]]() {})
    assertNotNull(responseBody)
    assertFalse(responseBody.isEmpty)
    assertEquals(1, responseBody.size)
    assertEquals("Brazil", responseBody.head.name.common)
    assertEquals("Federative Republic of Brazil", responseBody.head.name.official)
  }

}