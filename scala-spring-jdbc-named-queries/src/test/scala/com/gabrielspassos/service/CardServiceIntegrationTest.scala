package com.gabrielspassos.service

import com.gabrielspassos.Application
import com.gabrielspassos.dao.CardDAO
import com.gabrielspassos.entity.CardEntity
import org.junit.jupiter.api.Assertions.{assertFalse, assertNotNull}
import org.junit.jupiter.api.{AfterEach, Test, TestInstance}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan

import scala.collection.mutable.ListBuffer

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[Application])
)
@EnableAutoConfiguration
@ComponentScan(Array("com.*"))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CardServiceIntegrationTest @Autowired()(private val cardService: CardService,
                                              private val cardDAO: CardDAO) {

  private val cardEntities = ListBuffer[CardEntity]()

  @AfterEach
  def cleanUp(): Unit = {
    cardEntities.foreach(card => cardDAO.delete(card))
  }

  @Test
  def shouldFindCardsByInstitutionNameSuccessfully(): Unit = {
    def institutionNames = List("Fake Bank 002", "NuBank", "Banco do Brasil")

    val cards = cardService.findByInstitutionNameIn(institutionNames)

    assertNotNull(cards)
    assertFalse(cards.isEmpty)
  }
}

