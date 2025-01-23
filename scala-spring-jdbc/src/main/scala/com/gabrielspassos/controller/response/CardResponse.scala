package com.gabrielspassos.controller.response

import com.gabrielspassos.dto.CardDTO

class CardResponse(
 var id: String,
 var brand: String,
 var institutionName: String,
 var number: String,
 var name: String,
 var expirationDate: String,
 var cvv: String
) {
  def this() = this(null, null, null, null, null, null, null)

  def this(cardDTO: CardDTO) = this(
    if (cardDTO.id != null) cardDTO.id.toString else null,
    cardDTO.brand,
    cardDTO.institutionName,
    cardDTO.number,
    cardDTO.name,
    if (cardDTO.expirationDate != null) cardDTO.expirationDate.toString else null,
    cardDTO.cvv
  )

  def getId: String = id
  def setId(id: String): Unit = {
    this.id = id
  }
  
  def getInstitutionName: String = institutionName
  def setInstitutionName(institutionName: String): Unit = {
    this.institutionName = institutionName
  }
  
  def getBrand: String = brand
  def setBrand(brand: String): Unit = {
    this.brand = brand
  }
  
  def getNumber: String = number
  def setNumber(number: String): Unit = {
    this.number = number
  }
  
  def getName: String = name
  def setName(name: String): Unit = {
    this.name = name
  }

  def getExpirationDate: String = expirationDate
  def setExpirationDate(expirationDate: String): Unit = {
    this.expirationDate = expirationDate
  }
  
  def getCvv: String = cvv
  def setCvv(cvv: String): Unit = {
    this.cvv = cvv
  }
  
}
