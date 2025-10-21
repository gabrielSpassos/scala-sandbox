package com.gabrielspassos.repository

import com.gabrielspassos.entity.UserEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.util
import java.util.UUID

@Repository
trait UserRepository extends CrudRepository[UserEntity, UUID] {
  
}
