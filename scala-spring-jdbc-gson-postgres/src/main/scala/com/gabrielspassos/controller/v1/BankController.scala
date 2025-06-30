package com.gabrielspassos.controller.v1

import com.gabrielspassos.contract.impl.BankContractImpl
import com.gabrielspassos.contracts.v1.request.BankRequest
import com.gabrielspassos.contracts.v1.response.BankResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.*

import java.util
import java.util.List

@RestController
@RequestMapping(Array("/v1/banks"))
class BankController @Autowired()(private val bankContract: BankContractImpl) {

  @PostMapping
  def createBank(@RequestBody bankRequest: BankRequest): ResponseEntity[BankResponse] = {
    val bankResponse = bankContract.createBank(bankRequest)
    ResponseEntity.status(HttpStatus.CREATED).body(bankResponse)
  }

  @GetMapping()
  def findAll(): ResponseEntity[util.List[BankResponse]] = {
    val banks = bankContract.findAll()
    ResponseEntity.ok(banks)
  }
  
  @GetMapping(Array("/{code}"))
  def findByCode(@PathVariable(name = "code", required = true) code: String): ResponseEntity[BankResponse] = {
    Option(bankContract.findByCode(code)) match {
      case Some(bank) => ResponseEntity.ok(bank)
      case None => ResponseEntity.notFound().build()
    }
  }

  @DeleteMapping(Array("/{code}"))
  def deleteByCode(@PathVariable(name = "code", required = true) code: String): ResponseEntity[BankResponse] = {
    val bankResponse = bankContract.deleteByCode(code)
    ResponseEntity.ok(bankResponse)
  }
}
