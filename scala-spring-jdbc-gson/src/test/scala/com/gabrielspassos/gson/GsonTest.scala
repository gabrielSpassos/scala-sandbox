package com.gabrielspassos.gson

import com.gabrielspassos.contracts.v1.response.BankResponse
import com.google.gson.*
import org.junit.jupiter.api.Assertions.{assertEquals, assertNull}
import org.junit.jupiter.api.Test

class GsonTest {

  @Test
  def shouldReadJsonSuccessfully(): Unit = {
    // given
    val json = """{"code":"600", "name":"Fake bank"}"""

    // when
    val gson = new GsonBuilder().create()
    val bank = gson.fromJson(json, classOf[BankResponse])

    // then
    assertEquals("600", bank.getCode)
    assertEquals("Fake bank", bank.getName)
  }

  @Test
  def shouldReadJsonSuccessfullyWithNullValue(): Unit = {
    // given
    val json = """{"code":"600", "name":null}"""

    // when
    val gson = new GsonBuilder().create()
    val bank = gson.fromJson(json, classOf[BankResponse])

    // then
    assertEquals("600", bank.getCode)
    assertNull(bank.getName)
  }

  @Test
  def shouldReadJsonSuccessfullyWithAllNullValue(): Unit = {
    // given
    val json = """{"code":null, "name":null}"""

    // when
    val gson = new GsonBuilder().create()
    val bank = gson.fromJson(json, classOf[BankResponse])

    // then
    assertNull(bank.getCode)
    assertNull(bank.getName)
  }

  @Test
  def shouldReadJsonSuccessfullyWithImplicitNullValue(): Unit = {
    // given
    val json = """{"code":"600"}"""

    // when
    val gson = new GsonBuilder().create()
    val bank = gson.fromJson(json, classOf[BankResponse])

    // then
    assertEquals("600", bank.getCode)
    assertNull(bank.getName)
  }

  @Test
  def shouldReadJsonSuccessfullyWithImplicitAllNullValue(): Unit = {
    // given
    val json = """{}"""

    // when
    val gson = new GsonBuilder().create()
    val bank = gson.fromJson(json, classOf[BankResponse])

    // then
    assertNull(bank.getCode)
    assertNull(bank.getName)
  }

  @Test
  def shouldParseToJsonSuccessfully(): Unit = {
    // given
    val bankResponse = BankResponse("600", "Fake bank")
    val expectedJson = """{"code":"600","name":"Fake bank"}"""

    // when
    val gson = new GsonBuilder().create()
    val json = gson.toJson(bankResponse)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseToJsonSuccessfullyWithNullValue(): Unit = {
    // given
    val bankResponse = BankResponse("600", null)
    val expectedJson = """{"code":"600"}"""

    // when
    val gson = new GsonBuilder().create()
    val json = gson.toJson(bankResponse)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseToJsonSuccessfullyWithAllNullValue(): Unit = {
    // given
    val bankResponse = BankResponse()
    val expectedJson = """{}"""

    // when
    val gson = new GsonBuilder().create()
    val json = gson.toJson(bankResponse)

    // then
    assertEquals(expectedJson, json)
  }
}
