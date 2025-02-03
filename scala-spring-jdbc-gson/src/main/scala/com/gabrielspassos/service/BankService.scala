package com.gabrielspassos.service

import com.gabrielspassos.dao.BankDAO
import com.gabrielspassos.dto.BankDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BankService @Autowired()(private val bankDAO: BankDAO) {
  
  def save(bankDTO: BankDTO): BankDTO = {
    val bankEntity = bankDAO.save(BankDTO.fromDTOToEntity(bankDTO))
    BankDTO.toDTO(bankEntity)
  }

  def findByCode(code: String): Option[BankDTO] = {
    bankDAO.findByCode(code)
      .map(entity => BankDTO.toDTO(entity))
  }
  
  def deleteByCode(code: String): Either[String, BankDTO] = {
    bankDAO.findByCode(code) match
      case None => Left(s"Bank not found by code $code")
      case Some(bankEntity) =>
        val deletedBank = bankDAO.delete(bankEntity)
        Right(BankDTO.toDTO(deletedBank))
  }
  
}
