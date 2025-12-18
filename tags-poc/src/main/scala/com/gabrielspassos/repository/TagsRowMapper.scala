package com.gabrielspassos.repository

import com.gabrielspassos.entity.TagsEntity
import org.springframework.jdbc.core.RowMapper

import java.sql.ResultSet
import java.time.OffsetDateTime
import java.util.UUID

val tagsRowMapper: RowMapper[TagsEntity] =
  (rs: ResultSet, _: Int) =>
    TagsEntity(
      id = rs.getObject("id", classOf[UUID]),
      name = rs.getString("name"),
      entityId = rs.getString("entity_id"),
      value = rs.getString("value"),
      createdAt = rs.getObject("created_at", classOf[OffsetDateTime]),
      updatedAt = rs.getObject("updated_at", classOf[OffsetDateTime])
    )