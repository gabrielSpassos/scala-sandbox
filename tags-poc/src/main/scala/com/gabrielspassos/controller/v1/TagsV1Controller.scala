package com.gabrielspassos.controller.v1

import com.gabrielspassos.contract.impl.v1.TagsV1ContractImpl
import com.gabrielspassos.contracts.v1.request.TagsRequest
import com.gabrielspassos.contracts.v1.response.TagsResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{PathVariable, PostMapping, PutMapping, RequestBody, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/v1/tags"))
class TagsV1Controller @Autowired()(private val tagsContract: TagsV1ContractImpl) {
  
  @PutMapping(Array("/{id}"))
  def upsertTag(@PathVariable(name = "id", required = true) id: String,
                @RequestBody tagsRequest: TagsRequest
               ): ResponseEntity[TagsResponse] = {
    val isEnabled: Boolean = Option(tagsRequest).flatMap(r => Option(r.getIsEnabled)).exists(_.booleanValue())
    val tagsResponse = tagsContract.upsertTag(id, isEnabled)
    ResponseEntity.status(HttpStatus.OK).body(tagsResponse)
  }

}
