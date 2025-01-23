package com.gabrielspassos.dao.repository

import com.gabrielspassos.entity.CardEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.util.UUID

@Repository
trait CardRepository extends CrudRepository[CardEntity, UUID] {
  
  def findByNumber(number: String): CardEntity
  
}
