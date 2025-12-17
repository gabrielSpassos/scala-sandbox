package com.gabrielspassos.repository

import com.gabrielspassos.entity.TagsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait TagsRepository extends CrudRepository[TagsEntity, String] {

}