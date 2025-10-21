package com.gabrielspassos.config

import com.gabrielspassos.gson.{ListAdapter, LocalDateAdapter}
import com.google.gson.{Gson, GsonBuilder}
import org.springframework.context.annotation.{Bean, Configuration}

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
