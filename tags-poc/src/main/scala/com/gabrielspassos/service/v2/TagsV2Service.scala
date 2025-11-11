package com.gabrielspassos.service.v2

import com.gabrielspassos.dto.{ErrorDTO, InternalErrorDTO, NotFoundErrorDTO}
import com.gabrielspassos.entity.v2.TagsV2Entity
import com.gabrielspassos.repository.v2.TagsV2Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.stereotype.Service

import java.time.OffsetDateTime
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

@Service
class TagsV2Service @Autowired()(private val tagsV2Repository: TagsV2Repository,
                                 private val jdbcAggregateTemplate: JdbcAggregateTemplate) {

  def upsertTag(id: String, enabled: Boolean): Either[ErrorDTO, TagsV2Entity] = {
    try {
      for {
        entityOption <- findById(id)
        result <- entityOption match {
          case None =>
            val (enabledAt, disabledAt) = if (enabled) { (OffsetDateTime.now(), null) } else { (null, OffsetDateTime.now()) }
            val entity = TagsV2Entity(id = id, enabledAt = enabledAt, disabledAt = disabledAt)
            val insertedEntity = jdbcAggregateTemplate.insert(entity)
            Right(insertedEntity)
          case Some(existingTag) =>
            val (enabledAt, disabledAt) = if (enabled) { (OffsetDateTime.now(), null) } else { (null, OffsetDateTime.now()) }
            val updateEntity = existingTag.copy(enabledAt = enabledAt, disabledAt = disabledAt)
            val updatedEntity = tagsV2Repository.save(updateEntity)
            Right(updatedEntity)  
        }
      } yield result
    } catch {
      case e: Exception =>
        Left(InternalErrorDTO(s"Failed to upsert tag: $id"))
    }
  }
  
  def findById(id: String): Either[ErrorDTO, Option[TagsV2Entity]] = {
    try {
      Right(tagsV2Repository.findById(id).toScala)
    } catch {
      case e: Exception =>
        Left(InternalErrorDTO(s"Failed to find tag by id: $id"))
    }
  }

  def findByIds(ids: Seq[String]): Either[ErrorDTO, Seq[TagsV2Entity]] = {
    try {
      val entities = tagsV2Repository.findAllById(ids.asJava).asScala.toSeq
      Right(entities)
    } catch {
      case e: Exception =>
        Left(InternalErrorDTO(s"Failed to find tags by ids"))
    }
  }
  
  def deleteTag(id: String): Either[ErrorDTO, TagsV2Entity] = {
    for {
      entityOption <- findById(id)
      result <- entityOption match {
        case Some(existingTag) =>
          tagsV2Repository.delete(existingTag)
          Right(existingTag)
        case None =>
          Left(NotFoundErrorDTO("Tag not found"))
      }
    } yield result
  }

}
