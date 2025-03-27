package com.gabrielspassos.service

import com.gabrielspassos.controller.v1.request.ProductRequest
import com.gabrielspassos.dao.ProductDao
import com.gabrielspassos.entity.ProductEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired()(private val productDao: ProductDao) {

  def save(productRequest: ProductRequest): ProductEntity = {
    val productEntity = ProductEntity(
      id = null,
      name = productRequest.name,
      category = productRequest.category,
      value = productRequest.value,
      isActive = productRequest.isActive
    )
    productDao.save(productEntity)
  }

  def findByName(name: String): Option[ProductEntity] = {
    productDao.findByName(name)
  }

  def findAll(): List[ProductEntity] = {
    productDao.findAll()
  }

}
