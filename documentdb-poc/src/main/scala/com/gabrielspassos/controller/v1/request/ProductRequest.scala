package com.gabrielspassos.controller.v1.request

import org.springframework.data.mongodb.core.mapping.Field

import scala.annotation.meta.field

case class ProductRequest(
  name: String = null,
  category: String = null,
  value: BigDecimal = null,
  isActive: Boolean = false
)
