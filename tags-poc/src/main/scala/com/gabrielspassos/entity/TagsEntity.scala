package com.gabrielspassos.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.{Column, Table}

import java.time.OffsetDateTime
import java.util.UUID
import scala.annotation.meta.field

@Table(name = "tags_v3")
case class TagsEntity(
  @(Id @field)
  id: UUID = null,

  @(Column @field) 
  name: String,

  @(Column @field)(value = "entity_id")
  entityId: String,
  
  @(Column @field)
  value: String,
  
  @(Column @field)(value = "created_at")
  createdAt: OffsetDateTime = OffsetDateTime.now(),
  
  @(Column @field)(value = "updated_at")
  updatedAt: OffsetDateTime = OffsetDateTime.now(),
)
