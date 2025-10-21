package com.gabrielspassos.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.{Column, Table}

import java.time.{LocalDate, OffsetDateTime}
import java.util.UUID
import scala.annotation.meta.field

@Table(name = "user")
case class UserEntity(
  @(Id @field)
  id: UUID = null,
                     
  @(Column @field)(value = "user_id")
  userId: UUID = null,

  @(Column @field)
  cpf: String = null,

  @(Column @field)(value = "external_id1")
  externalId1: String,

  @(Column @field)(value = "external_id2")
  externalId2: String,

  @(Column @field)
  status: String,

  @(Column @field)(value = "created_at")
  createdAt: OffsetDateTime = OffsetDateTime.now(),

  @(Column @field)(value = "updated_at")
  updatedAt: OffsetDateTime = OffsetDateTime.now(),
)
