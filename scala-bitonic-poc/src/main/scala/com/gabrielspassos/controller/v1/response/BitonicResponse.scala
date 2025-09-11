package com.gabrielspassos.controller.v1.response

import com.gabrielspassos.entity.{BitonicSequenceCacheEntity, BitonicSequenceEntity}

class BitonicResponse(
  var id: String,
  var size: Int,
  var lowerBoundary: Int,
  var upperBoundary: Int,
  var sequence: Seq[Int]
) {
  
  def this() = this(null, 0, 0, 0, null)

  def this(bitonicSequenceCacheEntity: BitonicSequenceCacheEntity) = this(
    if (bitonicSequenceCacheEntity.id != null) bitonicSequenceCacheEntity.id.toString else null,
    bitonicSequenceCacheEntity.size,
    bitonicSequenceCacheEntity.lowerBoundary,
    bitonicSequenceCacheEntity.upperBoundary,
    bitonicSequenceCacheEntity.getSequence,
  )
  
  def this(bitonicSequenceEntity: BitonicSequenceEntity) = this(
    if (bitonicSequenceEntity.id != null) bitonicSequenceEntity.id.toString else null,
    bitonicSequenceEntity.size,
    bitonicSequenceEntity.lowerBoundary,
    bitonicSequenceEntity.upperBoundary,
    bitonicSequenceEntity.getSequence,
  )

  def getId: String = id

  def setId(id: String): Unit = {
    this.id = id
  }

  def getSize: Int = size
  
  def setSize(size: Int): Unit = {
    this.size = size
  }
  
  def getLowerBoundary: Int = lowerBoundary
  
  def setLowerBoundary(lowerBoundary: Int): Unit = {
    this.lowerBoundary = lowerBoundary
  }
  
  def getUpperBoundary: Int = upperBoundary
  
  def setUpperBoundary(upperBoundary: Int): Unit = {
    this.upperBoundary = upperBoundary
  }
  
  def getSequence: Seq[Int] = sequence
  
  def setSequence(sequence: Seq[Int]): Unit = {
    this.sequence = sequence
  }
  
  private def canEqual(other: Any): Boolean = other.isInstanceOf[BitonicResponse]
  
  override def equals(other: Any): Boolean = other match {
    case that: BitonicResponse =>
      that.canEqual(this) &&
        id == that.id &&
        size == that.size &&
        lowerBoundary == that.lowerBoundary &&
        upperBoundary == that.upperBoundary &&
        sequence == that.sequence
    case _ => false
  }
  
  override def hashCode(): Int = {
    val state = Seq(id, size, lowerBoundary, upperBoundary, sequence)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
  
  override def toString: String = s"BitonicResponse($id, $size, $lowerBoundary, $upperBoundary, $sequence)"
}
