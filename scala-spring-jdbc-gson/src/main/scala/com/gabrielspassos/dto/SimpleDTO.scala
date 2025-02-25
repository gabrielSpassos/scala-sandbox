package com.gabrielspassos.dto

import com.google.gson.annotations.SerializedName

class SimpleDTO(
  @SerializedName(value = "primary_key") private var id: String,
  @SerializedName("external_id") private var fk: String
) {

  // Empty constructor
  def this() = this(null, null)
  
  def getId: String = id
  def setId(id: String): Unit = {
    this.id = id
  }

  def getFk: String = fk

  def setFk(fk: String): Unit = {
    this.fk = fk
  }
}
