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
  
}
