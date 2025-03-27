package com.gabrielspassos.controller.v1

import com.gabrielspassos.controller.v1.request.ProductRequest
import com.gabrielspassos.entity.ProductEntity
import com.gabrielspassos.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, PostMapping, RequestBody, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/v1/products"))
class ProductController @Autowired()(private val productService: ProductService) {

  @PostMapping(Array(""))
  def createProduct(@RequestBody productRequest: ProductRequest): ResponseEntity[ProductEntity] = {
    val savedProductEntity = productService.save(productRequest)
    ResponseEntity.ok().body(savedProductEntity)
  }

  @GetMapping(Array("/product/{name}"))
  def findByName(@PathVariable name: String): ResponseEntity[ProductEntity] = {
    val productEntity = productService.findByName(name)
    productEntity match {
      case Some(entity) => ResponseEntity.ok().body(entity)
      case None => ResponseEntity.notFound().build()
    }
  }

  @GetMapping
  def findAll(): ResponseEntity[List[ProductEntity]] = {
    val productEntities = productService.findAll()
    ResponseEntity.ok().body(productEntities)
  }

}
