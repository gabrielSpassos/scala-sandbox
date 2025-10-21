package com.gabrielspassos.contract.impl.v1

import com.gabrielspassos.contracts.v1
import com.gabrielspassos.contracts.v1.response.ReportResponse
import com.gabrielspassos.service.v1.ReportService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ReportContractImpl @Autowired(private val reportService: ReportService) extends v1.ReportContract {

  override def createReport(externalId1: String): ReportResponse = {
    val entity = reportService.createReport(externalId1)

    val response = ReportResponse()
    response.setExternalId1(entity.externalId1)
    response.setContent(entity.content)

    response
  }

}
