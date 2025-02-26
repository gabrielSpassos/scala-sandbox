package com.gabrielspassos.dto

import com.google.gson.annotations.SerializedName

import scala.annotation.meta.field

case class SimpleCaseClassDTO(
  @(SerializedName @field)(value = "primary_key")
  id: String,
                             
  @(SerializedName @field)(value = "external_id")
  fk: String
)
