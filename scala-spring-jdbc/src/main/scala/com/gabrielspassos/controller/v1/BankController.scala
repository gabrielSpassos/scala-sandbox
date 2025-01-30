package com.gabrielspassos.controller.v1

import com.gabrielspassos.contract.impl.BankContractImpl
import com.gabrielspassos.contracts.v1.request.BankRequest
import com.gabrielspassos.contracts.v1.response.BankResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Array("/v1/banks"))
class BankController @Autowired()(private val bankContract: BankContractImpl) {

  @PostMapping
  def createBank(@RequestBody bankRequest: BankRequest): ResponseEntity[BankResponse] = {
    val bankResponse = bankContract.createBank(bankRequest)
    ResponseEntity.status(HttpStatus.CREATED).body(bankResponse)
  }
  
  @GetMapping(Array("/{code}"))
  def findByCode(@PathVariable(name = "code", required = true) code: String): ResponseEntity[BankResponse] = {
    Option(bankContract.findByCode(code)) match {
      case Some(bank) => ResponseEntity.ok(bank)
      case None => ResponseEntity.notFound().build()
    }
  }
}
