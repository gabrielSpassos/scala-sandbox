package com.gabrielspassos.controller

import com.gabrielspassos.controller.response.CardResponse
import com.gabrielspassos.service.CardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, RequestMapping, RestController}

import java.util
import java.util.stream.{Collectors, StreamSupport}
import scala.jdk.CollectionConverters.*

@RestController
@RequestMapping(Array("/cards"))
class CardController @Autowired()(private val cardService: CardService) {

  @GetMapping
  def findAll(): ResponseEntity[util.List[CardResponse]] = {
    val cards = StreamSupport.stream(cardService.findAll().asJava.spliterator(), false)
      .map(dto => new CardResponse(dto))
      .collect(Collectors.toList())
    ResponseEntity.ok(cards)
  }

  @GetMapping(Array("/{number}"))
  def findByNumber(@PathVariable("number") number: String): ResponseEntity[CardResponse] = {
    cardService.findByNumber(number) match {
      case Some(card) => ResponseEntity.ok(new CardResponse(card))
      case None => ResponseEntity.notFound().build()
    }
  }

}
