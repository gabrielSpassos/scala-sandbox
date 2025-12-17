package com.gabrielspassos.service

import com.gabrielspassos.dto.{ErrorDTO, InternalErrorDTO}
import com.gabrielspassos.entity.TagsEntity
import com.gabrielspassos.repository.{TagsRepository, tagsRowMapper}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import scala.jdk.CollectionConverters.*

@Service
class TagsService @Autowired()(private val tagsRepository: TagsRepository,
                               private val jdbcAggregateTemplate: JdbcAggregateTemplate,
                               private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) {

  def upsertTag(requestBody: Map[String, String]): Either[ErrorDTO, List[TagsEntity]] = {
    try {
      for {
        tagsKeys <- validateTagsKeys(requestBody.keys.toList)
        tagsEntity <- findByTagNameAndEntityId(tagsKeys)
        result = tagsKeys.map { case (tagName, entityId) =>
          val existingTag = tagsEntity.find(tag => tag.name == tagName && tag.entityId == entityId)
          val tagValue = requestBody(s"$tagName:$entityId")

          existingTag match {
            case Some(tag) =>
              // Update existing tag
              val updatedTag = tag.copy(value = tagValue)
              jdbcAggregateTemplate.save(updatedTag)
            case None =>
              // Insert new tag
              val newTag = TagsEntity(name = tagName, entityId = entityId, value = tagValue)
              jdbcAggregateTemplate.insert(newTag)
          }
        }
      } yield result
    } catch {
      case e: Exception =>
        Left(InternalErrorDTO(s"Failed to upsert tags"))
    }
  }

  private def validateTagsKeys(keys: List[String]): Either[ErrorDTO, List[(String, String)]] = {
    def validate(key: String): Either[ErrorDTO, (String, String)] = {
      val splitKey = key.split(":")

      if (splitKey.length != 2) {
        return Left(InternalErrorDTO(s"Invalid tag key format: $key"))
      }

      val tagName = splitKey(0).trim
      val entityId = splitKey(1).trim

      //todo: further validations can be added here
      Right((tagName, entityId))
    }
    val results = keys.map(validate)
    sequence(results)
  }

  private def sequence[A](eithers: List[Either[ErrorDTO, A]]): Either[ErrorDTO, List[A]] = {
    eithers.foldRight(Right(Nil): Either[ErrorDTO, List[A]]) {
      case (Right(value), Right(acc)) => Right(value :: acc)
      case (Left(err), _) => Left(err)
      case (_, Left(err)) => Left(err)
    }
  }

  private def findByTagNameAndEntityId(tagKeys: List[(String, String)]): Either[ErrorDTO, List[TagsEntity]] = {
    val sql = StringBuilder("""SELECT * FROM tags WHERE 1 = 1""")
    val params = MapSqlParameterSource()

    tagKeys.foreach { (tagName, entityId) =>
      sql.append(" OR (tag_name = :tagName AND entity_id = :entityId)")
      params.addValue("tagName", tagName)
      params.addValue("entityId", entityId)
    }

    val queryResults = namedParameterJdbcTemplate.queryForList(
      sql.toString(),
      params,
      classOf[TagsEntity]
    )
    
    //todo: proper handle failures here
    Right(queryResults.asScala.toList)
  }

}