package com.gabrielspassos.repository.v1

import com.gabrielspassos.entity.v1.TagsV1Entity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait TagsV1Repository extends CrudRepository[TagsV1Entity, String] {

}