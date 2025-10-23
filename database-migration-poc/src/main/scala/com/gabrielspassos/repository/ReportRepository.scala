package com.gabrielspassos.repository

import com.gabrielspassos.entity.ReportEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.lang.Long
import java.util.Optional

@Repository
trait ReportRepository extends CrudRepository[ReportEntity, Long] {

  def findByExternalId1(externalId1: String): Optional[ReportEntity]

  def findByUserId(userId: String): Optional[ReportEntity]

}
