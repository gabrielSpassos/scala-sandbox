package com.gabrielspassos.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.{Document, Field}

import java.util.UUID
import scala.annotation.meta.field

@Document(collection = "products")
case class ProductEntity(

  @(Id @field)
  id: UUID = null,

  @(Indexed @field)(unique = true)           
  name: String = null,

  category: String = null,

  @(Field @field)(name = "price")
  value: BigDecimal = null,

  isActive: Boolean = false,
)
