package com.gabrielspassos.repository

import com.gabrielspassos.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.util.UUID

@Repository
trait UserRepository extends CrudRepository[UserEntity, UUID] {

  def findByExternalId1AndExternalId2(externalId1: String, externalId2: String): UserEntity
  
}
