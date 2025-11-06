package com.gabrielspassos.gson

import com.gabrielspassos.contracts.v1.request.UserRequest
import com.google.gson.reflect.TypeToken
import com.google.gson.{Gson, GsonBuilder}
import org.junit.jupiter.api.Assertions.{assertEquals, assertNotNull, assertNull}
import org.junit.jupiter.api.Test

import java.time.LocalDate

class GsonTest {

  @Test
  def shouldReadJsonSuccessfully(): Unit = {
    // given
    val json = """{"externalId1":"1", "externalId2":"2"}"""
    val gson = createGson()

    // when
    val request = gson.fromJson(json, classOf[UserRequest])

    // then
    assertEquals("1", request.getExternalId1)
    assertEquals("2", request.getExternalId2)
  }

  @Test
  def shouldReadJsonSuccessfullyWithNullValue(): Unit = {
    // given
    val json = """{"externalId1":"1", "externalId2":null}"""
    val gson = createGson()

    // when
    val request = gson.fromJson(json, classOf[UserRequest])

    // then
    assertEquals("1", request.getExternalId1)
    assertNull(request.getExternalId2)
  }

  @Test
  def shouldReadJsonSuccessfullyWithAllNullValue(): Unit = {
    // given
    val json = """{"externalId1":null, "externalId2":null}"""
    val gson = createGson()

    // when
    val request = gson.fromJson(json, classOf[UserRequest])

    // then
    assertNull(request.getExternalId1)
    assertNull(request.getExternalId2)
  }

  @Test
  def shouldReadJsonSuccessfullyWithImplicitNullValue(): Unit = {
    // given
    val json = """{"externalId1":"1"}"""
    val gson = createGson()

    // when
    val request = gson.fromJson(json, classOf[UserRequest])

    // then
    assertEquals("1", request.getExternalId1)
    assertNull(request.getExternalId2)
  }

  @Test
  def shouldReadJsonSuccessfullyWithImplicitAllNullValue(): Unit = {
    // given
    val json = """{}"""
    val gson = createGson()

    // when
    val request = gson.fromJson(json, classOf[UserRequest])

    // then
    assertNull(request.getExternalId1)
    assertNull(request.getExternalId2)
  }

  @Test
  def shouldParseToJsonSuccessfully(): Unit = {
    // given
    val request = UserRequest("1", "2")
    val expectedJson = """{"externalId1":"1","externalId2":"2"}"""
    val gson = createGson()

    // when
    val json = gson.toJson(request)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseToJsonSuccessfullyWithNullValue(): Unit = {
    // given
    val request = UserRequest("1", null)
    val expectedJson = """{"externalId1":"1"}"""
    val gson = createGson()

    // when
    val json = gson.toJson(request)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseToJsonSuccessfullyWithAllNullValue(): Unit = {
    // given
    val request = UserRequest(null, null)
    val expectedJson = """{}"""
    val gson = createGson()

    // when
    val json = gson.toJson(request)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseList(): Unit = {
    val json = """[{"externalId1":"1", "externalId2":"2"}]"""
    val gson = createGson()

    val collectionType = new TypeToken[List[UserRequest]]() {}.getType
    val requests: List[UserRequest] = gson.fromJson(json, collectionType)

    assertNotNull(requests)
    assertEquals(1, requests.length)
    assertEquals("1", requests.head.getExternalId1)
    assertEquals("2", requests.head.getExternalId2)
  }

  private def createGson(): Gson = {
    new GsonBuilder()
      .registerTypeAdapter(classOf[List[?]], new ListAdapter[Any])
      .registerTypeAdapter(classOf[LocalDate], new LocalDateAdapter())
      .create()
  }
}
