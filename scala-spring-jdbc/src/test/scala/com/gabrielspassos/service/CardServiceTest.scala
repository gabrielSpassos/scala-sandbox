package com.gabrielspassos.service

import com.gabrielspassos.DataMock.{createCardEntity, createRandomCardNumber}
import com.gabrielspassos.dao.CardDAO
import com.gabrielspassos.entity.CardEntity
import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertNotNull}
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.when
import org.mockito.junit.jupiter.MockitoExtension

import java.util.UUID
import scala.compiletime.uninitialized

@ExtendWith(Array(classOf[MockitoExtension]))
class CardServiceTest {

  @Mock
  private var cardDAO: CardDAO = uninitialized

  @Test
  def shouldFindCards(): Unit = {
    val card1 = createCardEntity()
    val card2 = createCardEntity()
    val cardService = CardService(cardDAO = cardDAO)

    when(cardDAO.findAll()).thenReturn(List(card1, card2))

    val cards = cardService.findAll()

    assertNotNull(cards)
    assertFalse(cards.isEmpty)
    assertEquals(2, cards.size)
    assertEquals(card1.id, cards.head.id)
    assertEquals(card2.id, cards(1).id)
  }
}
