package com.gabrielspassos.dto

import com.gabrielspassos.contracts.v1.request.BankRequest
import com.gabrielspassos.contracts.v1.response.BankResponse
import com.gabrielspassos.entity.BankEntity

import java.util.UUID

case class BankDTO(
  id: UUID,
  code: String,
  name: String,
)

object BankDTO {
  def toDTO(bankEntity: BankEntity): BankDTO = {
    BankDTO(
      id = bankEntity.id,
      code = bankEntity.code,
      name = bankEntity.name,
    )
  }

  def toDTO(bankRequest: BankRequest): BankDTO = {
    BankDTO(
      id = null,
      code = bankRequest.getCode,
      name = bankRequest.getName,
    )
  }
  
  def fromDTOToResponse(bankDTO: BankDTO): BankResponse = {
    val bankResponse = BankResponse(bankDTO.code, bankDTO.name)
    bankResponse
  }

  def fromDTOToEntity(bankDTO: BankDTO): BankEntity = {
    val bankEntity = BankEntity(bankDTO.id, bankDTO.code, bankDTO.name)
    bankEntity
  }
}
