package com.gabrielspassos.contract.impl.v3

import com.gabrielspassos.contracts.v3.TagsContract
import com.gabrielspassos.contracts.v3.response.TagsResponse
import com.gabrielspassos.entity.v3.TagsV3Entity
import com.gabrielspassos.exception.HttpException
import com.gabrielspassos.service.v3.TagsV3Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

import java.util
import scala.jdk.CollectionConverters.*

@Component
class TagsV3ContractImpl @Autowired(private val tagsV3Service: TagsV3Service) extends TagsContract {
  override def upsertTag(id: String, value: String): TagsResponse = {
    tagsV3Service.upsertTag(id, value) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(tagEntity) =>
        buildV2Response(tagEntity)
    }
  }

  override def getTag(id: String): TagsResponse = {
    tagsV3Service.findById(id) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(None) =>
        throw HttpException(message = "Not found tag", httpStatus = HttpStatus.NOT_FOUND.value())
      case Right(Some(tagEntity)) =>
        buildV2Response(tagEntity)
    }
  }

  override def getTags(ids: util.List[String]): util.List[TagsResponse] = {
    tagsV3Service.findByIds(ids.asScala.toSeq) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(entities) =>
        entities.map { tagEntity => buildV2Response(tagEntity) }.toList.asJava
    }
  }

  override def deleteTag(id: String): TagsResponse = {
    tagsV3Service.deleteTag(id) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(tagEntity) =>
        buildV2Response(tagEntity)
    }
  }

  private def buildV2Response(tagsV3Entity: TagsV3Entity): TagsResponse = {
    val response = TagsResponse()
    response.setId(tagsV3Entity.id)
    response.setValue(tagsV3Entity.value)
    response
  }
}
