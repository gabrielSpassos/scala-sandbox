package com.gabrielspassos.controller.v2

import com.gabrielspassos.contract.impl.v2.ReportContractImplV2
import com.gabrielspassos.contracts.v2.response.ReportResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Array("/v2/reports"))
class ReportControllerV2 @Autowired()(private val reportContract: ReportContractImplV2) {

  @PostMapping(Array("/{userId}"))
  def createReport(@PathVariable(name = "userId", required = true) userId: String): ResponseEntity[ReportResponse] = {
    val reportResponse = reportContract.createReport(userId)
    ResponseEntity.status(HttpStatus.OK).body(reportResponse)
  }

}
