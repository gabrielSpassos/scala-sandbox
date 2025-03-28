package com.gabrielspassos.controller.v1.request

case class ProductRequest(
  name: String = null,
  category: String = null,
  value: BigDecimal = null,
  isActive: Boolean = false
)
