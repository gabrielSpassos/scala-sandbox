package com.gabrielspassos.dto

import com.gabrielspassos.entity.CardEntity

import java.time.LocalDate
import java.util.UUID

case class CardDTO(
 id: UUID,
 institutionName: String,
 brand: String,
 number: String,
 name: String,
 expirationDate: LocalDate,
 cvv: String
)

object CardDTO {
  def toDTO(cardEntity: CardEntity): CardDTO = {
    CardDTO(
      id = cardEntity.id,
      institutionName = cardEntity.institutionName,
      brand = cardEntity.brand,
      number = cardEntity.number,
      name = cardEntity.name,
      expirationDate = cardEntity.expirationDate,
      cvv = cardEntity.cvv
    )
  }
}