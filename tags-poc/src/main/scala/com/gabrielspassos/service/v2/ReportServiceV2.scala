package com.gabrielspassos.service.v2

import com.gabrielspassos.client.ReportClient
import com.gabrielspassos.entity.ReportEntity
import com.gabrielspassos.exception.{BadRequestException, NotFoundException}
import com.gabrielspassos.repository.ReportRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.jdk.OptionConverters.*


@Service
class ReportServiceV2 @Autowired()(private val userService: UserServiceV2,
                                   private val reportClient: ReportClient,
                                   private val reportRepository: ReportRepository) {
  
  def createReport(userId: String): ReportEntity = {
    val user = userService.findUserByUserId(userId) match {
      case None => throw new NotFoundException("Not found user")
      case Some(user) => user
    }
    
    if (!user.isActive) {
      throw new BadRequestException("User not active to create report")
    }
    
    val content = reportClient.generateReport()
    val report = reportRepository.findByUserId(userId).toScala match {
      case Some(report) =>
        report.copy(content = content)
      case None =>
        ReportEntity(
          id = null,
          userId = user.userId,
          externalId1 = null,
          content = content
        )
    }
    reportRepository.save(report)
  }
}
