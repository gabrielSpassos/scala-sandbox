package com.gabrielspassos.dao.repository

import com.gabrielspassos.entity.BitonicSequenceCacheEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.util.{Optional, UUID}

@Repository
trait BitonicSequenceCacheRepository extends CrudRepository[BitonicSequenceCacheEntity, UUID] {

  def findBySizeAndLowerBoundaryAndUpperBoundary(size: Int, lowerBoundary: Int, upperBoundary: Int): Optional[BitonicSequenceCacheEntity]

}

