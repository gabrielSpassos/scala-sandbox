package com.gabrielspassos.dao.cache.repository

import com.gabrielspassos.entity.BitonicSequenceCacheEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.util.{Optional, UUID}

@Repository
trait CacheRepository extends CrudRepository[BitonicSequenceCacheEntity, UUID] {

  def findBySizeAndLowerBoundaryAndUpperBoundary(size: Int, lowerBoundary: Int, upperBoundary: Int): Optional[BitonicSequenceCacheEntity]

}

