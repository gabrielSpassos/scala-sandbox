package com.gabrielspassos.dao.repository

import com.gabrielspassos.entity.BitonicSequenceEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.util
import java.util.UUID

@Repository
trait BitonicSequenceRepository extends CrudRepository[BitonicSequenceEntity, UUID] {
  
}
