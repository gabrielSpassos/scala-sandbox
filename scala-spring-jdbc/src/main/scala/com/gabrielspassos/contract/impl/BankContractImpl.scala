package com.gabrielspassos.contract.impl

import com.gabrielspassos.contracts.v1
import com.gabrielspassos.contracts.v1.request.BankRequest
import com.gabrielspassos.contracts.v1.response.BankResponse
import com.gabrielspassos.dto.BankDTO
import com.gabrielspassos.service.BankService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BankContractImpl @Autowired(private val bankService: BankService) extends v1.BankContract {

  override def createBank(bankRequest: BankRequest): BankResponse = {
    val bankDTO = BankDTO.toDTO(bankRequest)
    bankService.save(bankDTO) match
      case bank => BankDTO.fromDTOToResponse(bank)
  }
  
  override def findByCode(code: String): BankResponse = {
    bankService.findByCode(code) match
      case Some(bank) => BankDTO.fromDTOToResponse(bank)
      case _ => null
  }

  override def deleteByCode(code: String): BankResponse = {
    bankService.deleteByCode(code) match
      case Right(bank) => BankResponse()
      case Left(error) => throw new RuntimeException(error)
  }
    
}
