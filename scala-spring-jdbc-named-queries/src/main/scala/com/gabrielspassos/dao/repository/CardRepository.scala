package com.gabrielspassos.dao.repository

import com.gabrielspassos.entity.CardEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.util
import java.util.UUID

@Repository
trait CardRepository extends CrudRepository[CardEntity, UUID] {
  
  def findByNumberAndSoftDeletedFalse(number: String): CardEntity

  @Query("SELECT * FROM card WHERE institution_name in (:institutionNames) AND soft_deleted = false")
  def findByInstitutionNameIn(institutionNames: util.List[String]): util.List[CardEntity]

  @Query("SELECT * FROM card WHERE soft_deleted = false")
  def findAll(): util.List[CardEntity]
  
}
