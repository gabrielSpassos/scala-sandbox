package com.gabrielspassos.repository.v2

import com.gabrielspassos.entity.v2.TagsV2Entity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait TagsV2Repository extends CrudRepository[TagsV2Entity, String] {

}