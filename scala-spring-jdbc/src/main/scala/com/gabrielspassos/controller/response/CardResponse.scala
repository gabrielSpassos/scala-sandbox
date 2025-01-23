package com.gabrielspassos.controller.response

import java.util.UUID

class CardResponse(
 var id: UUID,
 var brand: String,
) {
  def this() = this(null, null)
  
  def getId: UUID = id
  def setId(id: UUID): Unit = {
    this.id = id
  }
  
  def getBrand: String = brand
  def setBrand(brand: String): Unit = {
    this.brand = brand
  }
  
}
