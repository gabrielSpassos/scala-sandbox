package com.gabrielspassos.service.v1

import com.gabrielspassos.client.ReportClient
import com.gabrielspassos.entity.ReportEntity
import com.gabrielspassos.exception.{BadRequestException, NotFoundException}
import com.gabrielspassos.repository.ReportRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.jdk.OptionConverters.*


@Service
class ReportService @Autowired()(private val userService: UserService,
                                 private val reportClient: ReportClient,
                                 private val reportRepository: ReportRepository) {
  
  def createReport(externalId1: String): ReportEntity = {
    val user = userService.findUserByExternalId1(externalId1) match {
      case None => throw new NotFoundException("Not found user")
      case Some(user) => user
    }
    
    if (!user.isActive) {
      throw new BadRequestException("User not active to create report")
    }

    val content = reportClient.generateReport()
    val report = reportRepository.findByExternalId1(externalId1).toScala match {
      case Some(report) =>
        report.copy(content = content)
      case None =>
        ReportEntity(
          id = null,
          userId = null,
          externalId1 = externalId1,
          content = content
        )
    }
    reportRepository.save(report)
  }
}
