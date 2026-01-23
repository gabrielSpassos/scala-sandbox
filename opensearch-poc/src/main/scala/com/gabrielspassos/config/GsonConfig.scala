package com.gabrielspassos.config

import com.gabrielspassos.gson.{ListAdapter, LocalDateAdapter}
import com.google.gson.{Gson, GsonBuilder}
import jakarta.json.spi.JsonProvider
import jakarta.json.stream.{JsonGenerator, JsonParser}
import org.opensearch.client.json.{JsonpDeserializer, JsonpMapperBase}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.stereotype.Component

import java.time.LocalDate

@Configuration
class GsonConfig {
  
  @Bean
  def gson: Gson = {
    new GsonBuilder()
      .registerTypeAdapter(classOf[List[?]], new ListAdapter[Any])
      .registerTypeAdapter(classOf[LocalDate], new LocalDateAdapter())
      .create()
  }
}

@Component
class GsonJsonpMapper @Autowired()(gson: Gson) extends JsonpMapperBase {

  private val provider = JsonProvider.provider()

  override def serialize[T](value: T, generator: JsonGenerator): Unit = {
    val json = gson.toJson(value)
    generator.write(json)
  }

  override def deserialize[T](parser: JsonParser, clazz: Class[T]): T = {
    val structure = parser.getValue
    gson.fromJson(structure.toString, clazz)
  }

  override def getDefaultDeserializer[T](clazz: Class[T]): JsonpDeserializer[T] = {
    JsonpDeserializer.of(clazz)
  }

  override def jsonProvider(): JsonProvider = provider
}
