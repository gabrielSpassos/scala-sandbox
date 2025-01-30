package com.gabrielspassos.controller.v1

import com.gabrielspassos.contracts.v1.BankControllerContract
import com.gabrielspassos.contracts.v1.response.BankResponse
import com.gabrielspassos.controller.v1.response.GenericApiResponse
import com.gabrielspassos.dto.BankDTO.fromDTOToResponse
import com.gabrielspassos.service.BankService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/v1/banks"))
class BankController @Autowired()(private val bankService: BankService) extends BankControllerContract {
  
  @GetMapping(Array("/{code}"))
  def findByCode(@PathVariable("code") code: String): GenericApiResponse[BankResponse] = {
    bankService.findByCode(code) match {
      case Some(bank) => GenericApiResponse.ok(fromDTOToResponse(bank))
      case None => GenericApiResponse.notFound(null)
    }
  }
}
