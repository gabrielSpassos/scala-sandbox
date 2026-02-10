package com.gabrielspassos.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.{Column, Table}

import java.time.Instant
import java.util.UUID
import scala.annotation.meta.field

@Table("items")
case class ItemEntity(
  @(Id @field)
  id: UUID = null,

  @(Column @field)(value = "external_id")
  externalId: String,

  @(Column @field)
  value: String,

  @(Column @field)
  updatedAt: Instant,
)
