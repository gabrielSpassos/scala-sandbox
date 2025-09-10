package com.gabrielspassos.controller.v1

import com.gabrielspassos.contract.impl.BitonicContractImpl
import com.gabrielspassos.controller.v1.request.BitonicRequest
import com.gabrielspassos.controller.v1.response.BitonicResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{PostMapping, RequestBody, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/v1/bitonic"))
class BitonicController @Autowired()(private val bitonicContract: BitonicContractImpl) {

  @PostMapping
  def createBank(@RequestBody bitonicRequest: BitonicRequest): ResponseEntity[BitonicResponse] = {
    val response = bitonicContract.createBitonicSequence(bitonicRequest)
    ResponseEntity.status(HttpStatus.OK).body(response)
  }

}
