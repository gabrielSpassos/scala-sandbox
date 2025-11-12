package com.gabrielspassos.service.v3

import com.gabrielspassos.dto.{BadRequestErrorDTO, ErrorDTO, InternalErrorDTO, NotFoundErrorDTO}
import com.gabrielspassos.entity.v3.TagsV3Entity
import com.gabrielspassos.repository.v3.TagsV3Repository
import com.gabrielspassos.validator.EnabledValueValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.stereotype.Service

import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

@Service
class TagsV3Service @Autowired()(private val tagsV3Repository: TagsV3Repository,
                                 private val jdbcAggregateTemplate: JdbcAggregateTemplate) {

  def upsertTag(id: String, value: String): Either[ErrorDTO, TagsV3Entity] = {
    try {
      for {
        entityOption <- findById(id)

        (isValidValue, enabledValue) = EnabledValueValidator.isValid(value)
        _ <- if (!isValidValue) { 
          Left(BadRequestErrorDTO(s"Failed to upsert tag: $id with invalid value")) 
        } else { Right(()) }
        
        result <- entityOption match {
          case None =>
            val entity = TagsV3Entity(id = id, value = enabledValue.valueAsString)
            val insertedEntity = jdbcAggregateTemplate.insert(entity)
            Right(insertedEntity)
          case Some(existingTag) =>
            val updateEntity = existingTag.copy(value = enabledValue.valueAsString)
            val updatedEntity = tagsV3Repository.save(updateEntity)
            Right(updatedEntity)  
        }
      } yield result
    } catch {
      case e: Exception =>
        Left(InternalErrorDTO(s"Failed to upsert tag: $id"))
    }
  }
  
  def findById(id: String): Either[ErrorDTO, Option[TagsV3Entity]] = {
    try {
      Right(tagsV3Repository.findById(id).toScala)
    } catch {
      case e: Exception =>
        Left(InternalErrorDTO(s"Failed to find tag by id: $id"))
    }
  }

  def findByIds(ids: Seq[String]): Either[ErrorDTO, Seq[TagsV3Entity]] = {
    try {
      val entities = tagsV3Repository.findAllById(ids.asJava).asScala.toSeq
      Right(entities)
    } catch {
      case e: Exception =>
        Left(InternalErrorDTO(s"Failed to find tags by ids"))
    }
  }
  
  def deleteTag(id: String): Either[ErrorDTO, TagsV3Entity] = {
    for {
      entityOption <- findById(id)
      result <- entityOption match {
        case Some(existingTag) =>
          tagsV3Repository.delete(existingTag)
          Right(existingTag)
        case None =>
          Left(NotFoundErrorDTO("Tag not found"))
      }
    } yield result
  }

}
