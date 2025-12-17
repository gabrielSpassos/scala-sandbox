package com.gabrielspassos.controller

import com.gabrielspassos.contract.impl.v5.TagsContractImpl
import com.gabrielspassos.contracts.v5.response.TagsValueResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{PutMapping, RequestBody, RequestMapping, RestController}

import java.util

@RestController
@RequestMapping(Array("/v5/tags"))
class TagsController @Autowired()(private val tagsContract: TagsContractImpl) {

  @PutMapping
  def upsertTag(@RequestBody requestBody: util.Map[String, String]): ResponseEntity[util.Map[String, TagsValueResponse]] = {
    val response = tagsContract.upsertTag(requestBody)
    ResponseEntity.status(HttpStatus.OK).body(response)
  }

}
