package com.gabrielspassos.gson

import com.gabrielspassos.contracts.v1.request.TagsRequest
import com.google.gson.reflect.TypeToken
import com.google.gson.{Gson, GsonBuilder}
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import java.time.LocalDate

class GsonTest {

  @Test
  def shouldReadJsonSuccessfully(): Unit = {
    // given
    val json = """{"isEnabled":true}"""
    val gson = createGson()

    // when
    val request = gson.fromJson(json, classOf[TagsRequest])

    // then
    assertTrue(request.getIsEnabled)
  }

  @Test
  def shouldReadJsonSuccessfullyWithNullValue(): Unit = {
    // given
    val json = """{"isEnabled":null}"""
    val gson = createGson()

    // when
    val request = gson.fromJson(json, classOf[TagsRequest])

    // then
    assertNull(request.getIsEnabled)
  }

  @Test
  def shouldReadJsonSuccessfullyWithImplicitNullValue(): Unit = {
    // given
    val json = """{}"""
    val gson = createGson()

    // when
    val request = gson.fromJson(json, classOf[TagsRequest])

    // then
    assertNull(request.getIsEnabled)
  }

  @Test
  def shouldParseToJsonSuccessfully(): Unit = {
    // given
    val request = TagsRequest(false)
    val expectedJson = """{"isEnabled":false}"""
    val gson = createGson()

    // when
    val json = gson.toJson(request)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseToJsonSuccessfullyWithNullValue(): Unit = {
    // given
    val request = TagsRequest(null)
    val expectedJson = """{}"""
    val gson = createGson()

    // when
    val json = gson.toJson(request)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseList(): Unit = {
    val json = """[{"isEnabled":true},{"isEnabled":false}]"""
    val gson = createGson()

    val collectionType = new TypeToken[List[TagsRequest]]() {}.getType
    val requests: List[TagsRequest] = gson.fromJson(json, collectionType)

    assertNotNull(requests)
    assertEquals(2, requests.length)
    assertTrue(requests.head.getIsEnabled)
    assertFalse(requests.last.getIsEnabled)
  }

  private def createGson(): Gson = {
    new GsonBuilder()
      .registerTypeAdapter(classOf[List[?]], new ListAdapter[Any])
      .registerTypeAdapter(classOf[LocalDate], new LocalDateAdapter())
      .create()
  }
}
