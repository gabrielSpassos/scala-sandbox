package com.gabrielspassos.contract.impl.v1

import com.gabrielspassos.contracts.v1.TagsContract
import com.gabrielspassos.contracts.v1.response.TagsResponse
import com.gabrielspassos.exception.HttpException
import com.gabrielspassos.service.v1.TagsV1Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class TagsV1ContractImpl @Autowired(private val tagsV1Service: TagsV1Service) extends TagsContract {
  override def upsertTag(id: String, isEnabled: Boolean): TagsResponse = {
    tagsV1Service.upsertTag(id, isEnabled) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(tagEntity) =>
        val response = TagsResponse()
        response.setId(tagEntity.id)
        response.setIsEnabled(tagEntity.enabled)
        response
    }
  }

  override def getTag(id: String): TagsResponse = {
    tagsV1Service.findById(id) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(None) =>
        throw HttpException(message = "Not found tag", httpStatus = HttpStatus.NOT_FOUND.value())
      case Right(Some(tagEntity)) =>
        val response = TagsResponse()
        response.setId(tagEntity.id)
        response.setIsEnabled(tagEntity.enabled)
        response
    }
  }

  override def deleteTag(id: String): TagsResponse = {
    tagsV1Service.deleteTag(id) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(tagEntity) =>
        val response = TagsResponse()
        response.setId(tagEntity.id)
        response.setIsEnabled(tagEntity.enabled)
        response
    }
  }
}
