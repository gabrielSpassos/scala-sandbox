package com.gabrielspassos.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.{JsonReader, JsonWriter}

import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter extends TypeAdapter[LocalDate] {
  @throws[IOException]
  override def write(out: JsonWriter, value: LocalDate): Unit = {
    out.value(value.format(LocalDateAdapter.formatter))
  }

  @throws[IOException]
  override def read(in: JsonReader): LocalDate = LocalDate.parse(in.nextString, LocalDateAdapter.formatter)
}

private object LocalDateAdapter {
  private val formatter = DateTimeFormatter.ISO_LOCAL_DATE
}