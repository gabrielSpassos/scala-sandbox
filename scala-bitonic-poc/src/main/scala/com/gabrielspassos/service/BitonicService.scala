package com.gabrielspassos.service

import com.gabrielspassos.controller.v1.request.BitonicRequest
import com.gabrielspassos.controller.v1.response.BitonicResponse
import com.gabrielspassos.dao.BitonicSequenceDAO
import com.gabrielspassos.dao.cache.CacheDAO
import com.gabrielspassos.entity.{BitonicSequenceCacheEntity, BitonicSequenceEntity}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.mutable

@Service
class BitonicService @Autowired(private val bitonicSequenceDAO: BitonicSequenceDAO,
                                private val bitonicSequenceCacheDAO: CacheDAO) {

  def createBitonicSequence(bitonicRequest: BitonicRequest): BitonicResponse = {
    val cachedValue = bitonicSequenceCacheDAO.findBySizeAndLowerBoundaryAndUpperBoundary(
      bitonicRequest.size, bitonicRequest.lowerBoundary, bitonicRequest.upperBoundary)
    if (cachedValue.isDefined) {
      println("Fetch bitonic sequence from cache")
      return BitonicResponse(cachedValue.get)
    }

    println("Creating bitonic sequence")
    val sequence = bitonicSequence(bitonicRequest.size, bitonicRequest.lowerBoundary, bitonicRequest.upperBoundary)
    val entity = BitonicSequenceEntity.fromSeq(
      id = null,
      size = bitonicRequest.size,
      lowerBoundary = bitonicRequest.lowerBoundary,
      upperBoundary = bitonicRequest.upperBoundary,
      sequence = sequence)
    val savedEntity = bitonicSequenceDAO.save(entity)
    println("Saved bitonic sequence on persist db")

    val cacheEntity = BitonicSequenceCacheEntity.fromSeq(
      id = null,
      size = bitonicRequest.size,
      lowerBoundary = bitonicRequest.lowerBoundary,
      upperBoundary = bitonicRequest.upperBoundary,
      sequence = sequence)
    bitonicSequenceCacheDAO.save(cacheEntity)
    println("Saved bitonic sequence on cache db")

    BitonicResponse(savedEntity)
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
