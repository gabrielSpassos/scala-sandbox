package com.gabrielspassos.repository

import com.gabrielspassos.entity.ItemEntity
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

import java.sql.Timestamp
import java.util.Optional
import scala.jdk.CollectionConverters.*

@Repository
class ItemDAO (jdbc: NamedParameterJdbcTemplate) extends ItemRepository {

  override def upsert(item: ItemEntity): Boolean = {
    val sql = """
      INSERT INTO items(id, value, updated_at)
      VALUES (:id, :value, :ts)
      ON CONFLICT(id)
      DO UPDATE
        SET value = EXCLUDED.value,
            updated_at = EXCLUDED.updated_at
      WHERE items.updated_at <= EXCLUDED.updated_at
      """

      val params = Map(
        "id" -> item.id,
        "value" -> item.value,
        "ts" -> Timestamp.from(item.updatedAt)
      ).asJava

      jdbc.update(sql, params) > 0
  }

  override def findById(id: String): Option[ItemEntity] = {
    val sql = "SELECT id,value,updated_at FROM items WHERE id=:id"

    val params = Map("id" -> id).asJava

    val list = jdbc.query(
        sql,
        params,
        (rs, _) =>
          ItemEntity(
            rs.getString("id"),
            rs.getString("value"),
            rs.getTimestamp("updated_at").toInstant
          )
      )

    if list.isEmpty then Option.empty[ItemEntity]
    else Option(list.get(0))
  }
}
