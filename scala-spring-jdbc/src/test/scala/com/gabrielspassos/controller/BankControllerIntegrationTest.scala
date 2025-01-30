package com.gabrielspassos.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.gabrielspassos.Application
import com.gabrielspassos.DataMock.createBankEntity
import com.gabrielspassos.contracts.v1.response.BankResponse
import com.gabrielspassos.controller.v1.response.GenericApiResponse
import com.gabrielspassos.dao.BankDAO
import org.junit.jupiter.api.Assertions.{assertEquals, assertNotNull, assertNull}
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
class BankControllerIntegrationTest @Autowired()(private val bankDAO: BankDAO) {

  @LocalServerPort
  var randomServerPort: Int = 0

  private val client = HttpClient.newHttpClient()
  private val objectMapper = ObjectMapper()

  @Test
  def shouldGetCardByNumberSuccessfully(): Unit = {
    val bank = createBankEntity().copy(id = null)
    val savedBank = bankDAO.save(bank)

    val url = s"http://localhost:$randomServerPort/v1/banks/${bank.code}"
    val response = client.send(
      HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = objectMapper.readValue(response.body(), classOf[GenericApiResponse[BankResponse]])
    val innerBody = objectMapper.readValue(objectMapper.writeValueAsString(responseBody.body), classOf[BankResponse])

    assertNotNull(responseBody)
    assertNotNull(responseBody.body)
    assertEquals(savedBank.code, innerBody.getCode)
    assertEquals(savedBank.name, innerBody.getName)
    assertEquals(200, responseBody.statusCode)
    assertEquals("OK", responseBody.message)
  }

  @Test
  def shouldNotFoundBankByCode(): Unit = {
    val url = s"http://localhost:$randomServerPort/v1/banks/99999"
    val response = client.send(
      HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
      HttpResponse.BodyHandlers.ofString()
    )

    assertEquals(200, response.statusCode())
    assertNotNull(response.body())

    val responseBody = objectMapper.readValue(response.body(), classOf[GenericApiResponse[BankResponse]])
    assertNotNull(responseBody)
    assertNull(responseBody.body)
    assertEquals(404, responseBody.statusCode)
    assertEquals("Not Found", responseBody.message)
  }

}