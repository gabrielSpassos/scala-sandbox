package com.gabrielspassos.dao.repository

import com.gabrielspassos.entity.BankEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.util
import java.util.UUID
import java.util

@Repository
trait BankRepository extends CrudRepository[BankEntity, UUID] {
  
  def findByCodeAndSoftDeletedFalse(code: String): BankEntity

  @Query("SELECT * FROM bank WHERE soft_deleted = false")
  def findAll(): util.List[BankEntity]
  
}
