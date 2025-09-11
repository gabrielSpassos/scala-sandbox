package com.gabrielspassos.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

import java.util.UUID
import scala.annotation.meta.field

@RedisHash(value = "bitonic_sequence")
case class BitonicSequenceCacheEntity(

  @(Id @field)
  id: UUID = null,
  @(Indexed @field)
  size: Int,
  @(Indexed @field)
  lowerBoundary: Int,
  @(Indexed @field)
  upperBoundary: Int,
  sequence: String,
                                     
) extends Serializable {
  def getSequence: Seq[Int] =
    if (sequence == null || sequence.isEmpty) Seq.empty
    else sequence.split(",").map(_.toInt).toSeq
}

object BitonicSequenceCacheEntity {
  def fromSeq(id: UUID,
              size: Int,
              lowerBoundary: Int,
              upperBoundary: Int,
              sequence: Seq[Int]
             ): BitonicSequenceCacheEntity =
    BitonicSequenceCacheEntity(id, size, lowerBoundary, upperBoundary, sequence.mkString(","))
}
