package com.gabrielspassos.gson

import com.gabrielspassos.DataMock
import com.gabrielspassos.DataMock.createGson
import com.gabrielspassos.controller.v1.response.BitonicResponse
import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.Assertions.{assertEquals, assertNotNull, assertNull}
import org.junit.jupiter.api.Test

import java.util.UUID

class GsonTest {

  @Test
  def shouldReadJsonSuccessfully(): Unit = {
    // given
    val id = UUID.randomUUID().toString
    val json = s"""{"id":"$id", "size":5, "lowerBoundary":3, "upperBoundary":10, "sequence":[9,10,9,8,7]}"""
    val gson = createGson

    // when
    val response = gson.fromJson(json, classOf[BitonicResponse])

    // then
    assertEquals(id, response.getId)
    assertEquals(5, response.getSize)
    assertEquals(3, response.getLowerBoundary)
    assertEquals(10, response.getUpperBoundary)
    assertEquals(Seq(9, 10, 9, 8, 7), response.getSequence)
  }

  @Test
  def shouldReadJsonSuccessfullyWithNullValue(): Unit = {
    // given
    val id = UUID.randomUUID().toString
    val json = s"""{"id":"$id", "size":null, "lowerBoundary":3, "upperBoundary":10, "sequence":[9,10,9,8,7]}"""
    val gson = createGson

    // when
    val response = gson.fromJson(json, classOf[BitonicResponse])

    // then
    assertEquals(id, response.getId)
    assertNull(response.getSize)
    assertEquals(3, response.getLowerBoundary)
    assertEquals(10, response.getUpperBoundary)
    assertEquals(Seq(9, 10, 9, 8, 7), response.getSequence)
  }

  @Test
  def shouldReadJsonSuccessfullyWithAllNullValue(): Unit = {
    // given
    val json = s"""{"id":null, "size":null, "lowerBoundary":null, "upperBoundary":null, "sequence":null}"""
    val gson = createGson

    // when
    val response = gson.fromJson(json, classOf[BitonicResponse])

    // then
    assertNull(response.getId)
    assertNull(response.getSize)
    assertNull(response.getLowerBoundary)
    assertNull(response.getUpperBoundary)
    assertNull(response.getSequence)
  }

  @Test
  def shouldReadJsonSuccessfullyWithImplicitNullValue(): Unit = {
    // given
    val id = UUID.randomUUID().toString
    val json = s"""{"id":"$id", "size":5, "upperBoundary":10, "sequence":[9,10,9,8,7]}"""
    val gson = createGson

    // when
    val response = gson.fromJson(json, classOf[BitonicResponse])

    // then
    assertEquals(id, response.getId)
    assertEquals(5, response.getSize)
    assertNull(response.getLowerBoundary)
    assertEquals(10, response.getUpperBoundary)
    assertEquals(Seq(9, 10, 9, 8, 7), response.getSequence)
  }

  @Test
  def shouldReadJsonSuccessfullyWithImplicitAllNullValue(): Unit = {
    // given
    val json = """{}"""
    val gson = createGson

    // when
    val response = gson.fromJson(json, classOf[BitonicResponse])

    // then
    assertNull(response.getId)
    assertNull(response.getSize)
    assertNull(response.getLowerBoundary)
    assertNull(response.getUpperBoundary)
    assertNull(response.getSequence)
  }

  @Test
  def shouldParseToJsonSuccessfully(): Unit = {
    // given
    val id = UUID.randomUUID().toString
    val bankResponse = BitonicResponse(id, 5, 3, 10, Seq(9, 10, 9, 8, 7))
    val expectedJson = s"""{"id":"$id", "size":5, "lowerBoundary":3, "upperBoundary":10, "sequence":[9,10,9,8,7]}"""
    val gson = createGson

    // when
    val json = gson.toJson(bankResponse)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseToJsonSuccessfullyWithNullValue(): Unit = {
    // given
    val id = UUID.randomUUID().toString
    val bankResponse = BitonicResponse(id, 5, 3, 0, Seq(9, 10, 9, 8, 7))
    val expectedJson = s"""{"id":"$id", "size":5, "lowerBoundary":3, "upperBoundary":10, "sequence":null}"""
    val gson = createGson

    // when
    val json = gson.toJson(bankResponse)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseToJsonSuccessfullyWithAllNullValue(): Unit = {
    // given
    val bankResponse = BitonicResponse()
    val expectedJson = """{}"""
    val gson = createGson

    // when
    val json = gson.toJson(bankResponse)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseList(): Unit = {
    val id = UUID.randomUUID().toString
    val json = s"""[{"id":"$id", "size":5, "lowerBoundary":3, "upperBoundary":10, "sequence":[9,10,9,8,7]}]"""
    val gson = createGson

    val collectionType = new TypeToken[List[BitonicResponse]]() {}.getType
    val responses: List[BitonicResponse] = gson.fromJson(json, collectionType)

    assertNotNull(responses)
    assertEquals(1, responses.size)
    assertEquals(id, responses.head.getId)
    assertEquals(5, responses.head.getSize)
    assertEquals(3, responses.head.getLowerBoundary)
    assertEquals(10, responses.head.getUpperBoundary)
    assertEquals(Seq(9, 10, 9, 8, 7), responses.head.getSequence)
  }

}
