package com.gabrielspassos.controller.v3

import com.gabrielspassos.contract.impl.v3.TagsV3ContractImpl
import com.gabrielspassos.contracts.v3.response.TagsResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.*

import java.util

@RestController
@RequestMapping(Array("/v3/tags"))
class TagsV3Controller @Autowired()(private val tagsContract: TagsV3ContractImpl) {
  
  @PutMapping(Array("/{id}/{value}"))
  def upsertTag(@PathVariable(name = "id", required = true) id: String,
                @PathVariable(name = "value", required = true) value: String,
               ): ResponseEntity[TagsResponse] = {
    val tagsResponse = tagsContract.upsertTag(id, value)
    ResponseEntity.status(HttpStatus.OK).body(tagsResponse)
  }

  @GetMapping(Array("/{id}"))
  def getTag(@PathVariable(name = "id", required = true) id: String): ResponseEntity[TagsResponse] = {
    val tagsResponse = tagsContract.getTag(id)
    ResponseEntity.status(HttpStatus.OK).body(tagsResponse)
  }

  @GetMapping
  def getTags(@RequestParam(name = "ids", required = true) ids: util.List[String]): ResponseEntity[util.List[TagsResponse]] = {
    val tagsResponses = tagsContract.getTags(ids)
    ResponseEntity.status(HttpStatus.OK).body(tagsResponses)
  }

  @DeleteMapping(Array("/{id}"))
  def deleteTag(@PathVariable(name = "id", required = true) id: String): ResponseEntity[TagsResponse] = {
    val tagsResponse = tagsContract.deleteTag(id)
    ResponseEntity.status(HttpStatus.OK).body(tagsResponse)
  }

}
