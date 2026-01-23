package com.gabrielspassos.gson

import com.gabrielspassos.dto.DocumentDTO
import com.google.gson.reflect.TypeToken
import com.google.gson.{Gson, GsonBuilder}
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import java.time.LocalDate

class GsonTest {

  @Test
  def shouldReadJsonSuccessfully(): Unit = {
    // given
    val json = """{"id":"1","title":"foo","content":"bar"}"""
    val gson = createGson()

    // when
    val parsed = gson.fromJson(json, classOf[DocumentDTO])

    // then
    assertNotNull(parsed)
    assertEquals("1", parsed.id)
    assertEquals("foo", parsed.title)
    assertEquals("bar", parsed.content)
  }

  @Test
  def shouldReadJsonSuccessfullyWithNullValue(): Unit = {
    // given
    val json = """{"id":"1","title":"foo","content":null}"""
    val gson = createGson()

    // when
    val parsed = gson.fromJson(json, classOf[DocumentDTO])

    // then
    assertNotNull(parsed)
    assertEquals("1", parsed.id)
    assertEquals("foo", parsed.title)
    assertNull(parsed.content)
  }

  @Test
  def shouldReadJsonSuccessfullyWithImplicitNullValue(): Unit = {
    // given
    val json = """{"id":"1","content":"bar"}"""
    val gson = createGson()

    // when
    val parsed = gson.fromJson(json, classOf[DocumentDTO])

    // then
    assertNotNull(parsed)
    assertEquals("1", parsed.id)
    assertNull(parsed.title)
    assertEquals("bar", parsed.content)
  }

  @Test
  def shouldParseToJsonSuccessfully(): Unit = {
    // given
    val documentDTO = DocumentDTO("1", "foo", "bar")
    val expectedJson = """{"id":"1","title":"foo","content":"bar"}"""
    val gson = createGson()

    // when
    val json = gson.toJson(documentDTO)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseToJsonSuccessfullyWithNullValue(): Unit = {
    // given
    val documentDTO = DocumentDTO("1", "foo", null)
    val expectedJson = """{"id":"1","title":"foo"}"""
    val gson = createGson()

    // when
    val json = gson.toJson(documentDTO)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseList(): Unit = {
    val json = """[{"id":"1","title":"foo"},{"id":"2","title":"tar"}]"""
    val gson = createGson()

    val collectionType = new TypeToken[List[DocumentDTO]]() {}.getType
    val dtos: List[DocumentDTO] = gson.fromJson(json, collectionType)

    assertNotNull(dtos)
    assertEquals(2, dtos.length)
    assertEquals("1", dtos.head.id)
    assertEquals("2", dtos.last.id)
  }

  private def createGson(): Gson = {
    new GsonBuilder()
      .registerTypeAdapter(classOf[List[?]], new ListAdapter[Any])
      .registerTypeAdapter(classOf[LocalDate], new LocalDateAdapter())
      .create()
  }
}
