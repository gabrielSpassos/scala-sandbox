package com.gabrielspassos.contract

import com.gabrielspassos.controller.v1.request.BitonicRequest
import com.gabrielspassos.controller.v1.response.BitonicResponse

trait BitonicContract {
  
  def createBitonicSequence(bitonicRequest: BitonicRequest): BitonicResponse

}
