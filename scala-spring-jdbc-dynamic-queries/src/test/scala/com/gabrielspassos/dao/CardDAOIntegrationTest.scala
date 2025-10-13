package com.gabrielspassos.dao

import com.gabrielspassos.{Application, BaseIntegrationTest}
import com.gabrielspassos.DataMock.createCardEntity
import com.gabrielspassos.entity.CardEntity
import org.junit.jupiter.api.Assertions.{assertEquals, assertNotNull}
import org.junit.jupiter.api.{AfterEach, Test, TestInstance}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan

import java.time.LocalDate
import scala.collection.mutable.ListBuffer

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[Application])
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CardDAOIntegrationTest @Autowired()(private val cardDAO: CardDAO) extends BaseIntegrationTest {

  private val cardEntities = ListBuffer[CardEntity]()

  @AfterEach
  def cleanUp(): Unit = {
    cardEntities.foreach(card => cardDAO.delete(card))
  }

  @Test
  def shouldReturnCardsByInstitutionNameAndBrandAndExpirationDate(): Unit = {
    val card1 = createCardEntity().copy(id = null, institutionName = "TestBank")
    val card2 = createCardEntity().copy(id = null, institutionName = "TestBank2")
    val card3 = createCardEntity().copy(id = null, institutionName = "TestBank")

    val savedCard1 = cardDAO.save(card1)
    val savedCard2 = cardDAO.save(card2)
    val savedCard3 = cardDAO.save(card3)
    cardEntities.addAll(Seq(savedCard1, savedCard2, savedCard3))

    val cards = cardDAO.findByDynamicParams("TestBank", "MasterCard", LocalDate.parse("2028-05-30"))

    assertNotNull(cards)
    assertEquals(2, cards.size)

    assertNotNull(cards.head.id)
    assertNotNull(cards.head.institutionName)
    assertNotNull(cards.head.brand)
    assertNotNull(cards.head.number)
    assertNotNull(cards.head.name)
    assertNotNull(cards.head.expirationDate)
    assertNotNull(cards.head.cvv)

    assertNotNull(cards.last.id)
    assertNotNull(cards.last.institutionName)
    assertNotNull(cards.last.brand)
    assertNotNull(cards.last.number)
    assertNotNull(cards.last.name)
    assertNotNull(cards.last.expirationDate)
    assertNotNull(cards.last.cvv)
  }

}
