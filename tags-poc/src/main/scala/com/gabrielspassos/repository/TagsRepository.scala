package com.gabrielspassos.repository

import com.gabrielspassos.entity.TagsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
trait TagsRepository extends CrudRepository[TagsEntity, String] {

  def findByNameAndEntityId(name: String, entityId: String): Optional[TagsEntity]

}