package com.gabrielspassos.controller.v1.request

import com.gabrielspassos.entity.BitonicSequenceEntity

class BitonicRequest(
  var id: String,
  var size: Int,
  var lowerBoundary: Int,
  var upperBoundary: Int,
  var sequence: Seq[Int]
) {
  
  def this() = this(null, 0, 0, 0, null)

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
  
  private def canEqual(other: Any): Boolean = other.isInstanceOf[BitonicRequest]
  
  override def equals(other: Any): Boolean = other match {
    case that: BitonicRequest =>
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
  
  override def toString: String = s"BitonicRequest($id, $size, $lowerBoundary, $upperBoundary, $sequence)"
}
