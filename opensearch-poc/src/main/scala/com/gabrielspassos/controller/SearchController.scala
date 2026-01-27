package com.gabrielspassos.controller

import com.gabrielspassos.dto.DocumentDTO
import com.gabrielspassos.service.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RequestBody, RequestMapping, RequestParam, RestController}

@RestController
@RequestMapping(Array("/v1/docs"))
class SearchController @Autowired()(private val searchService: SearchService) {

  @PostMapping(Array("/index"))
  def createIndex(): ResponseEntity[String] = {
    val index = searchService.createIndex()
    ResponseEntity.status(HttpStatus.CREATED).body(index)
  }

  @PostMapping
  def add(@RequestBody doc: DocumentDTO): ResponseEntity[String] = {
    val index = searchService.save(doc)
    ResponseEntity.status(HttpStatus.CREATED).body(index)
  }

  @GetMapping
  def search(@RequestParam q: String): ResponseEntity[List[DocumentDTO]] = {
    val documents = searchService.search(q)
    ResponseEntity.ok(documents)
  }
}
