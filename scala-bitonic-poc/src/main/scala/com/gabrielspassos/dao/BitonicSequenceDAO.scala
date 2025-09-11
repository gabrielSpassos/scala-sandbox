package com.gabrielspassos.dao

import com.gabrielspassos.dao.repository.BitonicSequenceRepository
import com.gabrielspassos.entity.BitonicSequenceEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.util.UUID
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

@Component
class BitonicSequenceDAO @Autowired()(private val bitonicSequenceRepository: BitonicSequenceRepository) {

  def findById(id: UUID): Option[BitonicSequenceEntity] = {
    bitonicSequenceRepository.findById(id).toScala
  }

  def save(bitonicSequence: BitonicSequenceEntity): BitonicSequenceEntity = {
    bitonicSequenceRepository.save(bitonicSequence)
  }
  
  def delete(bitonicSequence: BitonicSequenceEntity): BitonicSequenceEntity = {
    bitonicSequenceRepository.delete(bitonicSequence)
    bitonicSequence
  }
  
  def findAll(): List[BitonicSequenceEntity] = {
    bitonicSequenceRepository.findAll().asScala.toList
  }

}
