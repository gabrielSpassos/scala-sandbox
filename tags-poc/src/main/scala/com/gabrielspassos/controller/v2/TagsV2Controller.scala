package com.gabrielspassos.controller.v2

import com.gabrielspassos.contract.impl.v2.TagsV2ContractImpl
import com.gabrielspassos.contracts.v2.request.TagsRequest
import com.gabrielspassos.contracts.v2.response.TagsResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.*

import java.util

@RestController
@RequestMapping(Array("/v2/tags"))
class TagsV2Controller @Autowired()(private val tagsContract: TagsV2ContractImpl) {
  
  @PutMapping(Array("/{id}"))
  def upsertTag(@PathVariable(name = "id", required = true) id: String,
                @RequestBody tagsRequest: TagsRequest
               ): ResponseEntity[TagsResponse] = {
    val isEnabled: Boolean = Option(tagsRequest).flatMap(r => Option(r.getIsEnabled)).exists(_.booleanValue())
    val tagsResponse = tagsContract.upsertTag(id, isEnabled)
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
