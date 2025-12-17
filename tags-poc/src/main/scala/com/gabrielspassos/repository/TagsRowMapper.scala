package com.gabrielspassos.repository

import com.gabrielspassos.entity.TagsEntity
import org.springframework.jdbc.core.RowMapper

import java.sql.ResultSet
import java.time.OffsetDateTime
import java.util.UUID

val tagsRowMapper: RowMapper[TagsEntity] =
  (rs: ResultSet, _: Int) =>
    TagsEntity(
      id = UUID.fromString(rs.getString("id")),
      name = rs.getString("name"),
      entityId = rs.getString("entity_id"),
      value = rs.getString("value"),
      createdAt = OffsetDateTime.parse(rs.getString("created_at")),
      updatedAt = OffsetDateTime.parse(rs.getString("updated_at"))
    )
