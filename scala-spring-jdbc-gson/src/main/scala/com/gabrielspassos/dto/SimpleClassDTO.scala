package com.gabrielspassos.dto

import com.google.gson.annotations.SerializedName

class SimpleClassDTO {

  @SerializedName(value = "primary_key")
  private var id: String = null
  
  @SerializedName("external_id")
  private var fk: String = null
  
  def getId: String = id
  
  def setId(id: String): Unit = {
    this.id = id
  }
  
  def getFk: String = fk
  
  def setFk(fk: String): Unit = {
    this.fk = fk
  }
  
}
