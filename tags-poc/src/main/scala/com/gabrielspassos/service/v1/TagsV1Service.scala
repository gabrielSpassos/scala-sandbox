package com.gabrielspassos.service.v1

import com.gabrielspassos.dto.{ErrorDTO, InternalErrorDTO, NotFoundErrorDTO}
import com.gabrielspassos.entity.v1.TagsV1Entity
import com.gabrielspassos.repository.v1.TagsV1Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.stereotype.Service

import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

@Service
class TagsV1Service @Autowired()(private val tagsV1Repository: TagsV1Repository,
                                 private val jdbcAggregateTemplate: JdbcAggregateTemplate) {

  def upsertTag(id: String, enabled: Boolean): Either[ErrorDTO, TagsV1Entity] = {
    try {
      for {
        entityOption <- findById(id)
        result <- entityOption match {
          case None =>
            val entity = TagsV1Entity(id = id, enabled = enabled)
            val insertedEntity = jdbcAggregateTemplate.insert(entity)
            Right(insertedEntity)
          case Some(existingTag) =>
            val updateEntity = existingTag.copy(enabled = enabled)
            val updatedEntity = tagsV1Repository.save(updateEntity)
            Right(updatedEntity)  
        }
      } yield result
    } catch {
      case e: Exception =>
        Left(InternalErrorDTO(s"Failed to upsert tag: $id"))
    }
  }
  
  def findById(id: String): Either[ErrorDTO, Option[TagsV1Entity]] = {
    try {
      Right(tagsV1Repository.findById(id).toScala)
    } catch {
      case e: Exception =>
        Left(InternalErrorDTO(s"Failed to find tag by id: $id"))
    }
  }

  def findByIds(ids: Seq[String]): Either[ErrorDTO, Seq[TagsV1Entity]] = {
    try {
      val entities = tagsV1Repository.findAllById(ids.asJava).asScala.toSeq
      Right(entities)
    } catch {
      case e: Exception =>
        Left(InternalErrorDTO(s"Failed to find tags by ids"))
    }
  }
  
  def deleteTag(id: String): Either[ErrorDTO, TagsV1Entity] = {
    for {
      entityOption <- findById(id)
      result <- entityOption match {
        case Some(existingTag) =>
          tagsV1Repository.delete(existingTag)
          Right(existingTag)
        case None =>
          Left(NotFoundErrorDTO("Tag not found"))
      }
    } yield result
  }

}
