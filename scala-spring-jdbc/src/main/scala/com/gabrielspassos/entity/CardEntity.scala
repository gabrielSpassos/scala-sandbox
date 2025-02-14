package com.gabrielspassos.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.{Column, Table}

import java.time.LocalDate
import java.util.UUID
import scala.annotation.meta.field

@Table(name = "CARD")
case class CardEntity(
  @(Id @field)
  id: UUID = null,

  @(Column @field)(value = "INSTITUTION_NAME")
  institutionName: String,

  @(Column @field)
  brand: String,

  @(Column @field)
  number: String,

  @(Column @field)
  name: String,

  @(Column @field)(value = "EXPIRATION_DATE")
  expirationDate: LocalDate,

  @(Column @field)
  cvv: String
)
