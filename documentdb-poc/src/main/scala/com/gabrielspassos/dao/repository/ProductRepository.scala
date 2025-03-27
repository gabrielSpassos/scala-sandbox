package com.gabrielspassos.dao.repository

import com.gabrielspassos.entity.ProductEntity
import org.springframework.data.mongodb.repository.MongoRepository

trait ProductRepository extends MongoRepository[ProductEntity, String] {

  def findByName(name: String): Option[ProductEntity]

}
