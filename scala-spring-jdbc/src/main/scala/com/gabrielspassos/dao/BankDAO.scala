package com.gabrielspassos.dao

import com.gabrielspassos.dao.repository.BankRepository
import com.gabrielspassos.entity.BankEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class BankDAO @Autowired()(private val jdbcTemplate: JdbcTemplate,
                           private val bankRepository: BankRepository) {

  def insertBasicData(): Unit = {
    jdbcTemplate.execute("INSERT INTO bank (code, name) VALUES ('001', 'Banco do Brasil')")
    jdbcTemplate.execute("INSERT INTO bank (code, name) VALUES ('104', 'Caixa Economica')")
    println("Inserted basic bank data via jdbcTemplate")

    val bank = BankEntity(code = "260", name = "NuBank")
    save(bank)
    println("Inserted basic bank data via repository")
  }

  def findByCode(code: String): Option[BankEntity] = {
    bankRepository.findByCode(code) match
      case null => None
      case bank => Option(bank)
  }

  def save(bankEntity: BankEntity): BankEntity = {
    bankRepository.save(bankEntity)
  }

}
