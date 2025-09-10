package com.gabrielspassos.controller.v1.request

class BitonicRequest(
  var size: Int = 0,
  var lowerBoundary: Int = 0,
  var upperBoundary: Int = 0,
) {
  
  def this() = this(0, 0, 0)

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
  
  private def canEqual(other: Any): Boolean = other.isInstanceOf[BitonicRequest]
  
  override def equals(other: Any): Boolean = other match {
    case that: BitonicRequest =>
      that.canEqual(this) &&
        size == that.size &&
        lowerBoundary == that.lowerBoundary &&
        upperBoundary == that.upperBoundary
    case _ => false
  }
  
  override def hashCode(): Int = {
    val state = Seq(size, lowerBoundary, upperBoundary)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
  
  override def toString: String = s"BitonicRequest($size, $lowerBoundary, $upperBoundary)"
}
