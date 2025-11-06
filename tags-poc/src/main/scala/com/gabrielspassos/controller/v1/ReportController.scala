package com.gabrielspassos.controller.v1

import com.gabrielspassos.contract.impl.v1.ReportContractImpl
import com.gabrielspassos.contracts.v1.response.ReportResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Array("/v1/reports"))
class ReportController @Autowired()(private val reportContract: ReportContractImpl) {

  @PostMapping(Array("/{externalId1}"))
  def createReport(@PathVariable(name = "externalId1", required = true) externalId1: String): ResponseEntity[ReportResponse] = {
    val reportResponse = reportContract.createReport(externalId1)
    ResponseEntity.status(HttpStatus.OK).body(reportResponse)
  }

}
