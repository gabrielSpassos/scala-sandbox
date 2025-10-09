package com.gabrielspassos.dao

import com.gabrielspassos.dao.repository.CardRepository
import com.gabrielspassos.entity.CardEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

import java.time.LocalDate
import scala.jdk.CollectionConverters.*

@Component
class CardDAO @Autowired()(private val jdbcTemplate: JdbcTemplate,
                           private val cardRepository: CardRepository) {

  def insertBasicData(): Unit = {
    try {
      jdbcTemplate.execute("INSERT INTO card (institution_name, brand, number, name, expiration_date, cvv) " +
        "VALUES ('NuBank', 'MasterCard', '5162305254037431', 'John Smith', '2030-12-30', '123')")
      jdbcTemplate.execute("INSERT INTO card (id, institution_name, brand, number, name, expiration_date, cvv) " +
        "VALUES (gen_random_uuid(), 'NuBank', 'Visa', '5162306263962296', 'Mary Book', '2027-08-30', '456')")
      jdbcTemplate.execute("INSERT INTO card (id, institution_name, brand, number, name, expiration_date, cvv, soft_deleted) " +
        "VALUES (gen_random_uuid(), 'Fake Bank 002', 'MasterCard', '5162306263962638', 'Mary Book', '2027-08-30', '987', true)")
      println("Inserted basic card data via jdbcTemplate")

      val card = CardEntity(
        institutionName = "Banco do Brasil",
        brand = "MasterCard",
        number = "5548652238579391",
        name = "Jack Doo",
        expirationDate = LocalDate.parse("2028-05-30"),
        cvv = "987"
      )
      save(card)
      println("Inserted basic card data via repository")

      val softDeletedCard = CardEntity(
        institutionName = "Fake Bank 002",
        brand = "MasterCard",
        number = "5548652238574367",
        name = "Jack Doo",
        expirationDate = LocalDate.parse("2028-05-30"),
        cvv = "999",
        softDeleted = true
      )
      save(softDeletedCard)
      println("Inserted soft deleted card data via repository")
    } catch {
      case e: Exception =>
        println("Error inserting basic card data")
        e.printStackTrace()
    }
  }

  def findAll(): List[CardEntity] = {
    cardRepository.findAll().asScala.toList
  }

  def findByNumber(number: String): Option[CardEntity] = {
    cardRepository.findByNumberAndSoftDeletedFalse(number) match {
      case null => None
      case card => Some(card)
    }
  }
  
  def findByInstitutionNameIn(institutionNames: List[String]): List[CardEntity] = {
    cardRepository.findByInstitutionNameIn(institutionNames.asJava).asScala.toList
  }

  def findByBrandIn(brands: List[String]): List[CardEntity] = {
    cardRepository.findByBrandInAndSoftDeletedFalse(brands.asJava).asScala.toList
  }

  def save(cardEntity: CardEntity): CardEntity = {
    cardRepository.save(cardEntity)
  }

  def delete(cardEntity: CardEntity): CardEntity = {
    cardRepository.delete(cardEntity)
    cardEntity
  }

}
