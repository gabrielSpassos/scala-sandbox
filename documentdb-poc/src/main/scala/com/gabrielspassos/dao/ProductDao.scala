package com.gabrielspassos.dao

import com.gabrielspassos.dao.repository.ProductRepository
import com.gabrielspassos.entity.ProductEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.jdk.CollectionConverters.*

@Component
class ProductDao @Autowired()(private val productRepository: ProductRepository) {

  def save(productEntity: ProductEntity): ProductEntity = {
    productRepository.save(productEntity)
  }

  def findByName(name: String): Option[ProductEntity] = {
    productRepository.findByName(name)
  }
  
  def findAll(): List[ProductEntity] = {
    productRepository.findAll().asScala.toList
  }

}
