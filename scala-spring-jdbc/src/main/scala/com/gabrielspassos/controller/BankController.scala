package com.gabrielspassos.controller

import com.gabrielspassos.contracts.BankControllerContract
import com.gabrielspassos.contracts.response.BankResponse
import com.gabrielspassos.dto.BankDTO.fromDTOToResponse
import com.gabrielspassos.service.BankService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/banks"))
class BankController @Autowired()(private val bankService: BankService) extends BankControllerContract {
  
  @GetMapping(Array("/{code}"))
  def findByCode(@PathVariable("code") code: String): ResponseEntity[BankResponse] = {
    bankService.findByCode(code) match {
      case Some(bank) => ResponseEntity.ok(fromDTOToResponse(bank))
      case None => ResponseEntity.notFound().build()
    }
  }
}
