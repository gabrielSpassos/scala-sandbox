package com.gabrielspassos.contract.impl.v2

import com.gabrielspassos.contracts.v2.TagsContract
import com.gabrielspassos.contracts.v2.response.TagsResponse
import com.gabrielspassos.entity.v2.TagsV2Entity
import com.gabrielspassos.exception.HttpException
import com.gabrielspassos.service.v2.TagsV2Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class TagsV2ContractImpl @Autowired(private val tagsV2Service: TagsV2Service) extends TagsContract {
  override def upsertTag(id: String, isEnabled: Boolean): TagsResponse = {
    tagsV2Service.upsertTag(id, isEnabled) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(tagEntity) =>
        buildV2Response(tagEntity)
    }
  }

  override def getTag(id: String): TagsResponse = {
    tagsV2Service.findById(id) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(None) =>
        throw HttpException(message = "Not found tag", httpStatus = HttpStatus.NOT_FOUND.value())
      case Right(Some(tagEntity)) =>
        buildV2Response(tagEntity)
    }
  }

  override def deleteTag(id: String): TagsResponse = {
    tagsV2Service.deleteTag(id) match {
      case Left(error) =>
        throw HttpException(message = error.getMessage, httpStatus = error.getStatus)
      case Right(tagEntity) =>
        buildV2Response(tagEntity)
    }
  }

  private def buildV2Response(tagsV2Entity: TagsV2Entity): TagsResponse = {
    val response = TagsResponse()
    response.setId(tagsV2Entity.id)
    response.setEnabledAt(Option(tagsV2Entity.enabledAt).map(_.toString).orNull)
    response.setDisabledAt(Option(tagsV2Entity.disabledAt).map(_.toString).orNull)
    response
  }
}
