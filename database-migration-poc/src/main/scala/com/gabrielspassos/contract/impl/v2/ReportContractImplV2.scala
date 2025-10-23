package com.gabrielspassos.contract.impl.v2

import com.gabrielspassos.contracts.v2
import com.gabrielspassos.contracts.v2.response.ReportResponse
import com.gabrielspassos.service.v2.ReportServiceV2
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ReportContractImplV2 @Autowired(private val reportService: ReportServiceV2) extends v2.ReportContract {

  override def createReport(userId: String): ReportResponse = {
    val entity = reportService.createReport(userId)

    val response = ReportResponse()
    response.setContent(entity.content)

    response
  }

}
