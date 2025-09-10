package com.gabrielspassos

import com.gabrielspassos.entity.BitonicSequenceEntity
import com.gabrielspassos.gson.{ListAdapter, LocalDateAdapter}
import com.google.gson.{Gson, GsonBuilder}

import java.time.LocalDate
import java.util.UUID
import scala.util.Random

object DataMock {

  def createRandomCardNumber(): String = {
    val randomValue = Random.between(111111111111L, 999999999999L).toString
    "5548" + randomValue
  }
  
  def createGson: Gson = {
    new GsonBuilder()
      .registerTypeAdapter(classOf[List[?]], new ListAdapter[Any])
      .registerTypeAdapter(classOf[LocalDate], new LocalDateAdapter())
      .create()
  }
}
