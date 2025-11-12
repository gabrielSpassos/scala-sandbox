package com.gabrielspassos.repository.v3

import com.gabrielspassos.entity.v3.TagsV3Entity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait TagsV3Repository extends CrudRepository[TagsV3Entity, String] {

}