package com.gabrielspassos.dao.row.mapper

import com.gabrielspassos.entity.CardEntity
import org.springframework.jdbc.core.RowMapper

import java.sql.ResultSet
import java.util.UUID

class CardRowMapper extends RowMapper[CardEntity]:
  override def mapRow(rs: ResultSet, rowNum: Int): CardEntity =
    CardEntity(
      id = UUID.fromString(rs.getString("id")),
      institutionName = rs.getString("institution_name"),
      brand = rs.getString("brand"),
      number = rs.getString("number"),
      name = rs.getString("name"),
      expirationDate = rs.getDate("expiration_date").toLocalDate,
      cvv = rs.getString("cvv"),
      softDeleted = rs.getBoolean("soft_deleted")
    )