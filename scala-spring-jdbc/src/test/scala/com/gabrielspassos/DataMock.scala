package com.gabrielspassos

import com.gabrielspassos.entity.CardEntity

import java.time.LocalDate
import java.util.UUID
import scala.util.Random

object DataMock {

  def createRandomCardNumber(): String = {
    val randomValue = Random.between(111111111111L, 999999999999L).toString
    "5548" + randomValue
  }
  
  def createCardEntity(): CardEntity = {
    CardEntity(
      id = UUID.randomUUID(),
      institutionName = "NuBank",
      brand = "MasterCard",
      number = createRandomCardNumber(),
      name = "Teste Tester",
      expirationDate = LocalDate.parse("2028-05-30"),
      cvv = Random().between(111, 999).toString
    )
  }
}
