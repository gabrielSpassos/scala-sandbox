package com.gabrielspassos.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.{Column, Table}

import java.util.UUID
import scala.annotation.meta.field

@Table(name = "bank")
case class BankEntity(
  @(Id @field)
  id: UUID = null,

  @(Column @field)
  code: String,
  
  @(Column @field)
  name: String,

  @(Column @field)(value = "soft_deleted")
  softDeleted: Boolean = false,
                     
)
