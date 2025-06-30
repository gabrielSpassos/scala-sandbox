package com.gabrielspassos.dao

import com.gabrielspassos.dao.repository.BankRepository
import com.gabrielspassos.entity.BankEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

import scala.jdk.CollectionConverters.*

@Component
class BankDAO @Autowired()(private val jdbcTemplate: JdbcTemplate,
                           private val bankRepository: BankRepository) {

  def insertBasicData(): Unit = {
    try {
      jdbcTemplate.execute("INSERT INTO bank (code, name) VALUES ('001', 'Banco do Brasil')")
      jdbcTemplate.execute("INSERT INTO bank (code, name) VALUES ('104', 'Caixa Economica')")
      jdbcTemplate.execute("INSERT INTO bank (code, name, soft_deleted) VALUES ('002', 'Fake Bank 002', true)")
      println("Inserted basic bank data via jdbcTemplate")

      val bank = BankEntity(code = "260", name = "NuBank")
      save(bank)
      println("Inserted basic bank data via repository")

      val softDeleted = BankEntity(code = "003", name = "Fake Bank 003", softDeleted = true)
      save(softDeleted)
      println("Inserted soft deleted bank data via repository")
    } catch {
      case e: Exception =>
        println("Error inserting basic bank data")
        e.printStackTrace()
    }
  }

  def findByCode(code: String): Option[BankEntity] = {
    bankRepository.findByCodeAndSoftDeletedFalse(code) match
      case null => None
      case bank => Option(bank)
  }

  def save(bankEntity: BankEntity): BankEntity = {
    bankRepository.save(bankEntity)
  }
  
  def delete(bankEntity: BankEntity): BankEntity = {
    bankRepository.delete(bankEntity)
    bankEntity
  }
  
  def findAll(): List[BankEntity] = {
    bankRepository.findAll().asScala.toList
  }

}
