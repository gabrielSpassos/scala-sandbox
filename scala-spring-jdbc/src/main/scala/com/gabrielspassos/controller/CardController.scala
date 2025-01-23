package com.gabrielspassos.controller

import com.gabrielspassos.controller.response.CardResponse
import com.gabrielspassos.service.CardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, RequestMapping, RestController}

import java.util
import scala.jdk.CollectionConverters.*

@RestController
@RequestMapping(Array("/cards"))
class CardController @Autowired()(private val cardService: CardService) {

  @GetMapping
  def findAll(): ResponseEntity[util.List[CardResponse]] = {
    val cards = cardService.findAll()
      .map(dto => new CardResponse(dto))
      .asJava
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
