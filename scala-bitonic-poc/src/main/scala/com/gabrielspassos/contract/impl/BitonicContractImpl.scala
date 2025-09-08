package com.gabrielspassos.contract.impl

import com.gabrielspassos.contract.BitonicContract
import com.gabrielspassos.controller.v1.request.BitonicRequest
import com.gabrielspassos.controller.v1.response.BitonicResponse
import com.gabrielspassos.service.BitonicService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BitonicContractImpl @Autowired(private val bitonicService: BitonicService) extends BitonicContract {

  override def createBitonicSequence(bitonicRequest: BitonicRequest): BitonicResponse = {
    val entity = bitonicService.createBitonicSequence(bitonicRequest)
    BitonicResponse(entity)
  }
}
