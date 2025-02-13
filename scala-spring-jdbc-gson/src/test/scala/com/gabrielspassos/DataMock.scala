package com.gabrielspassos

import com.gabrielspassos.entity.{BankEntity, CardEntity}
import com.gabrielspassos.gson.{ListAdapter, LocalDateAdapter}
import com.google.gson.{Gson, GsonBuilder}

import java.time.LocalDate
import java.util.UUID
import scala.util.Random

object DataMock {

  def createRandomCardNumber(): String = {
    val randomValue = Random.between(111111111111L, 999999999999L).toString
    "5548" + randomValue
  }
  
  def createRandomCVV(): String = {
    Random().between(111, 999).toString
  }
  
  def createCardEntity(id: UUID = UUID.randomUUID(),
                       number: String = createRandomCardNumber(),
                       cvv: String = createRandomCVV()): CardEntity = {
    CardEntity(
      id = id,
      institutionName = "NuBank",
      brand = "MasterCard",
      number = number,
      name = "Teste Tester",
      expirationDate = LocalDate.parse("2028-05-30"),
      cvv = cvv
    )
  }
  
  def createBankEntity(id: UUID = UUID.randomUUID()): BankEntity = {
    BankEntity(
      id = id,
      code = "341",
      name = "Itau"
    )
  }

  def createGson: Gson = {
    new GsonBuilder()
      .registerTypeAdapter(classOf[List[?]], new ListAdapter[Any])
      .registerTypeAdapter(classOf[LocalDate], new LocalDateAdapter())
      .create()
  }
}
