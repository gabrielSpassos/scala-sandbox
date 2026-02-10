package com.gabrielspassos.repository.v1

import com.gabrielspassos.entity.ItemEntity
import com.gabrielspassos.repository.v1.ItemRepositoryV1
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

import java.sql.Timestamp
import java.util.UUID
import scala.jdk.CollectionConverters.*

@Repository
class ItemDAOV1(jdbc: NamedParameterJdbcTemplate) extends ItemRepositoryV1 {

  override def upsert(item: ItemEntity): Boolean = {
    val sql = """
      INSERT INTO items(external_id, value, updated_at)
      VALUES (:externalId, :value, :ts)
      ON CONFLICT(external_id)
      DO UPDATE
        SET value = EXCLUDED.value,
            updated_at = EXCLUDED.updated_at
      WHERE items.updated_at <= EXCLUDED.updated_at
      """

    val params = Map(
      "externalId" -> item.externalId,
      "value" -> item.value,
      "ts" -> Timestamp.from(item.updatedAt)
    ).asJava

    jdbc.update(sql, params) > 0
  }

  override def findByExternalId(externalId: String): Option[ItemEntity] = {
    val sql = "SELECT id,external_id,value,updated_at FROM items WHERE external_id=:externalId"

    val params = Map("externalId" -> externalId).asJava

    val list = jdbc.query(
        sql,
        params,
        (rs, _) =>
          ItemEntity(
            UUID.fromString(rs.getString("id")),
            rs.getString("external_id"),
            rs.getString("value"),
            rs.getTimestamp("updated_at").toInstant
          )
      )

    if list.isEmpty then Option.empty[ItemEntity]
    else Option(list.get(0))
  }
}
