package com.gabrielspassos.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.{Column, Table}

import java.util.UUID
import scala.annotation.meta.field

@Table(name = "bitonic_sequence")
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
  sequence: String,
) {
  def getSequence: Seq[Int] =
    if (sequence == null || sequence.isEmpty) Seq.empty
    else sequence.split(",").map(_.toInt).toSeq
}

object BitonicSequenceEntity {
  def fromSeq(id: UUID,
              size: Int,
              lowerBoundary: Int,
              upperBoundary: Int,
              sequence: Seq[Int]
             ): BitonicSequenceEntity =
    BitonicSequenceEntity(id, size, lowerBoundary, upperBoundary, sequence.mkString(","))
}
