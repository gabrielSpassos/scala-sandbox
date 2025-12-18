package com.gabrielspassos.contract.impl.v5

import com.gabrielspassos.contracts.v5.TagsContract
import com.gabrielspassos.contracts.v5.response.TagsValueResponse
import com.gabrielspassos.entity.TagsEntity
import com.gabrielspassos.exception.HttpException
import com.gabrielspassos.service.TagsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.{lang, util}
import scala.jdk.CollectionConverters.*

@Component
class TagsContractImpl @Autowired(private val tagsService: TagsService) extends TagsContract {

  override def upsertTag(requestBody: util.Map[String, String]): util.Map[String, TagsValueResponse] = {
    tagsService.upsertTag(requestBody.asScala.toMap) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(tags) =>
        buildTagsResponse(tags)
    }
  }

  override def getTags(keys: util.List[String]): util.Map[String, TagsValueResponse] = {
    tagsService.getTags(keys.asScala.toList) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(tags) =>
        buildTagsResponse(tags)
    }
  }

  override def deleteTag(key: String): lang.Boolean = {
    tagsService.deleteTag(key) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(result) =>
        result
    }
  }
  
  private def buildTagsResponse(tags: List[TagsEntity]): util.Map[String, TagsValueResponse] = {
    tags.map(tag => s"${tag.name}:${tag.entityId}" -> buildTagsValueResponse(tag)).toMap.asJava
  }
  
  private def buildTagsValueResponse(tagEntity: TagsEntity): TagsValueResponse = {
    TagsValueResponse(
      tagEntity.value,
      Option(tagEntity.createdAt).map(_.toString).orNull,
      Option(tagEntity.updatedAt).map(_.toString).orNull
    )
  }
}