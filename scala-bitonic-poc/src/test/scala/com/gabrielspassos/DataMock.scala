package com.gabrielspassos

import com.gabrielspassos.gson.{ListAdapter, LocalDateAdapter, SeqAdapter}
import com.google.gson.{Gson, GsonBuilder}

import java.time.LocalDate

object DataMock {
  
  def createGson: Gson = {
    new GsonBuilder()
      .registerTypeAdapter(classOf[List[?]], new ListAdapter[Any])
      .registerTypeAdapter(classOf[Seq[?]], new SeqAdapter[Any])
      .registerTypeAdapter(classOf[LocalDate], new LocalDateAdapter())
      .create()
  }
}
