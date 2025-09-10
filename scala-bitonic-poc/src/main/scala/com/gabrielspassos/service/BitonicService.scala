package com.gabrielspassos.service

import com.gabrielspassos.controller.v1.request.BitonicRequest
import com.gabrielspassos.dao.BitonicSequenceDAO
import com.gabrielspassos.entity.BitonicSequenceEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.mutable

@Service
class BitonicService @Autowired(private val bitonicSequenceDAO: BitonicSequenceDAO) {

  def createBitonicSequence(bitonicRequest: BitonicRequest): BitonicSequenceEntity = {
    val sequence = bitonicSequence(bitonicRequest.size, bitonicRequest.lowerBoundary, bitonicRequest.upperBoundary)
    val entity = BitonicSequenceEntity.fromSeq(
      id = null,
      size = bitonicRequest.size,
      lowerBoundary = bitonicRequest.lowerBoundary,
      upperBoundary = bitonicRequest.upperBoundary,
      sequence = sequence)
    bitonicSequenceDAO.save(entity)
  }
  
  private def bitonicSequence(num: Int, lower: Int, upper: Int): Seq[Int] =
    // If sequence is not possible
    if num > (upper - lower) * 2 + 1 then
      return Seq(-1)

    // Store the resultant list
    val ans = mutable.ArrayDeque[Int]()

    for i <- 0 until math.min(upper - lower + 1, num - 1) do
      ans.addOne(upper - i)

    // If size of deque < n
    for i <- 0 until (num - ans.size) do
      // Add elements from start
      ans.prepend(upper - i - 1)

    // Print the stored list
    println(ans)

    ans.toSeq
}
