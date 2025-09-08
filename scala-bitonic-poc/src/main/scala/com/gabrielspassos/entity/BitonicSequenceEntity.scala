package com.gabrielspassos.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.{Column, Table}

import java.util.UUID
import scala.annotation.meta.field

@Table(name = "bank")
case class BitonicSequenceEntity(
  @(Id @field)
  id: UUID = null,

  @(Column @field)
  size: Int,

  @(Column @field)(value = "lower_boundary")
  lowerBoundary: Int,

  @(Column @field)(value = "upper_boundary")
  upperBoundary: Int,

  @(Column @field)
  sequence: Seq[Int],                          
)
