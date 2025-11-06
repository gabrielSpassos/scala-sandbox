package com.gabrielspassos.contract.impl.v1

import com.gabrielspassos.contracts.v1
import com.gabrielspassos.contracts.v1.TagsContract
import com.gabrielspassos.contracts.v1.response.TagsResponse
import com.gabrielspassos.service.v1.TagsV1Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TagsV1ContractImpl @Autowired(private val userService: TagsV1Service) extends TagsContract {
  override def upsertTag(id: String, isEnabled: Boolean): TagsResponse = {
    val tagEntity = userService.upsertTag(id, isEnabled)
    
    val response = TagsResponse()
    response.setId(tagEntity.id)
    response.setIsEnabled(tagEntity.enabled)
    response
  }
}
