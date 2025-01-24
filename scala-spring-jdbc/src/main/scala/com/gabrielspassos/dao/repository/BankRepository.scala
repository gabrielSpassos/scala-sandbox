package com.gabrielspassos.dao.repository

import com.gabrielspassos.entity.BankEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.util.UUID

@Repository
trait BankRepository extends CrudRepository[BankEntity, UUID] {
  
  def findByCode(code: String): BankEntity
  
}
