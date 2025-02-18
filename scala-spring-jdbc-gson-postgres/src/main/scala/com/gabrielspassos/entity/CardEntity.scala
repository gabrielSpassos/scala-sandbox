package com.gabrielspassos.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.{Column, Table}

import java.time.LocalDate
import java.util.UUID
import scala.annotation.meta.field

@Table(name = "card")
case class CardEntity(
  @(Id @field)
  id: UUID = null,

  @(Column @field)(value = "institution_name")
  institutionName: String,

  @(Column @field)
  brand: String,

  @(Column @field)
  number: String,

  @(Column @field)
  name: String,

  @(Column @field)(value = "expiration_date")
  expirationDate: LocalDate,

  @(Column @field)
  cvv: String
)
