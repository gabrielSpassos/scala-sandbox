package com.gabrielspassos.controller.v5

import com.gabrielspassos.contract.impl.v5.TagsContractImpl
import com.gabrielspassos.contracts.v5.response.TagsValueResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.*

import java.util

@RestController
@RequestMapping(Array("/v5/tags"))
class TagsController @Autowired()(private val tagsContract: TagsContractImpl) {

  @PutMapping
  def upsertTag(@RequestBody requestBody: util.Map[String, String]): ResponseEntity[util.Map[String, TagsValueResponse]] = {
    val response = tagsContract.upsertTag(requestBody)
    ResponseEntity.status(HttpStatus.OK).body(response)
  }

  @GetMapping
  def getTags(@RequestParam(name = "keys", required = true) keys: util.List[String]): ResponseEntity[util.Map[String, TagsValueResponse]]  = {
    val response = tagsContract.getTags(keys)
    ResponseEntity.status(HttpStatus.OK).body(response)
  }

  @DeleteMapping(Array("/{key}"))
  def deleteTag(@PathVariable(name = "key", required = true) id: String): ResponseEntity[HttpStatus] = {
    val tagsResponse = tagsContract.deleteTag(id)
    ResponseEntity.status(HttpStatus.NO_CONTENT).build()
  }


}
