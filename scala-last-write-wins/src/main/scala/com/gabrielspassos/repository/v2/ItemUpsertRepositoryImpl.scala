package com.gabrielspassos.repository.v2

import com.gabrielspassos.entity.ItemEntity
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

import java.sql.Timestamp
import scala.jdk.CollectionConverters.*

@Repository
class ItemUpsertRepositoryImpl(jdbc: NamedParameterJdbcTemplate) extends ItemUpsertRepository {

  override def upsert(item: ItemEntity): Boolean = {
    val sql =
      """
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
  
}
