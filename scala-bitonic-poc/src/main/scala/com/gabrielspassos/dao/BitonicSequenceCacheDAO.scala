package com.gabrielspassos.dao

import com.gabrielspassos.dao.repository.BitonicSequenceCacheRepository
import com.gabrielspassos.entity.BitonicSequenceCacheEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.jdk.OptionConverters.*

@Component
class BitonicSequenceCacheDAO @Autowired()(private val bitonicSequenceCacheRepository: BitonicSequenceCacheRepository) {

  def findBySizeAndLowerBoundaryAndUpperBoundary(size: Int,
                                                 lowerBoundary: Int,
                                                 upperBoundary: Int): Option[BitonicSequenceCacheEntity] ={
    bitonicSequenceCacheRepository.findBySizeAndLowerBoundaryAndUpperBoundary(size, lowerBoundary, upperBoundary).toScala
  }

  def save(bitonicSequenceCacheEntity: BitonicSequenceCacheEntity): BitonicSequenceCacheEntity = {
    bitonicSequenceCacheRepository.save(bitonicSequenceCacheEntity)
  }

  def delete(bitonicSequenceCacheEntity: BitonicSequenceCacheEntity): BitonicSequenceCacheEntity = {
    bitonicSequenceCacheRepository.delete(bitonicSequenceCacheEntity)
    bitonicSequenceCacheEntity
  }
}

