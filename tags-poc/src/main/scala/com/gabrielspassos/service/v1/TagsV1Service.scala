package com.gabrielspassos.service.v1

import com.gabrielspassos.entity.v1.TagsV1Entity
import com.gabrielspassos.exception.BadRequestException
import com.gabrielspassos.repository.v1.TagsV1Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.jdk.OptionConverters.*

@Service
class TagsV1Service @Autowired()(private val tagsV1Repository: TagsV1Repository) {

  def upsertTag(id: String, enabled: Boolean): TagsV1Entity = {
    val tagEntity = findById(id) match {
      case None => 
        TagsV1Entity(
          id = id,
          enabled = enabled
        )
      case Some(existingTag) =>
        existingTag.copy(enabled = enabled)
    }
    
    tagsV1Repository.save(tagEntity)
  }
  
  def findById(id: String): Option[TagsV1Entity] = {
    tagsV1Repository.findById(id).toScala
  }

}
