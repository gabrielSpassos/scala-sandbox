package com.gabrielspassos.dao.cache

import com.gabrielspassos.dao.cache.repository.CacheRepository
import com.gabrielspassos.entity.BitonicSequenceCacheEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.jdk.OptionConverters.*

@Component
class CacheDAO @Autowired()(private val cacheRepository: CacheRepository) {

  def findBySizeAndLowerBoundaryAndUpperBoundary(size: Int,
                                                 lowerBoundary: Int,
                                                 upperBoundary: Int): Option[BitonicSequenceCacheEntity] ={
    cacheRepository.findBySizeAndLowerBoundaryAndUpperBoundary(size, lowerBoundary, upperBoundary).toScala
  }

  def save(bitonicSequenceCacheEntity: BitonicSequenceCacheEntity): BitonicSequenceCacheEntity = {
    cacheRepository.save(bitonicSequenceCacheEntity)
  }

  def delete(bitonicSequenceCacheEntity: BitonicSequenceCacheEntity): BitonicSequenceCacheEntity = {
    cacheRepository.delete(bitonicSequenceCacheEntity)
    bitonicSequenceCacheEntity
  }
}

