package com.gabrielspassos.service

import com.gabrielspassos.controller.v1.request.BitonicRequest
import com.gabrielspassos.dao.BitonicSequenceDAO
import com.gabrielspassos.dao.cache.BitonicSequenceCacheDAO
import com.gabrielspassos.entity.{BitonicSequenceCacheEntity, BitonicSequenceEntity}
import org.junit.jupiter.api.Assertions.{assertEquals, assertNotNull}
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.when
import org.mockito.junit.jupiter.MockitoExtension

import java.util.UUID
import scala.compiletime.uninitialized

@ExtendWith(Array(classOf[MockitoExtension]))
class BitonicServiceTest {

  @Mock
  private var bitonicSequenceDAO: BitonicSequenceDAO = uninitialized

  @Mock
  private var bitonicSequenceCacheDAO: BitonicSequenceCacheDAO = uninitialized

  @Test
  def shouldCreateBitonicSequence(): Unit = {
    val request = createRequest()
    val entity = createEntity()
    val entityToSave = entity.copy(id = null)
    val cacheEntity = createCacheEntity()
    val cacheEntityToSave = cacheEntity.copy(id = null)
    val bitonicService = createBitonicService()

    when(bitonicSequenceCacheDAO.findBySizeAndLowerBoundaryAndUpperBoundary(5, 3, 10)).thenReturn(None)
    when(bitonicSequenceDAO.save(entityToSave)).thenReturn(entity)
    when(bitonicSequenceCacheDAO.save(cacheEntityToSave)).thenReturn(cacheEntity)

    val response = bitonicService.createBitonicSequence(request)

    assertNotNull(response)
    assertNotNull(response.id)
    assertEquals(5, response.size)
    assertEquals(3, response.lowerBoundary)
    assertEquals(10, response.upperBoundary)
    assertEquals(Seq(9, 10, 9, 8, 7), response.sequence)
  }

  @Test
  def shouldCreateInvalidBitonicSequence(): Unit = {
    val request = createRequest(size = 5, lowerBoundary = 3, upperBoundary = 3)
    val entity = createEntity(size = 5, lowerBoundary = 3, upperBoundary = 3, sequence = Seq(-1))
    val entityToSave = entity.copy(id = null)
    val cacheEntity = createCacheEntity(size = 5, lowerBoundary = 3, upperBoundary = 3, sequence = Seq(-1))
    val cacheEntityToSave = cacheEntity.copy(id = null)
    val bitonicService = createBitonicService()

    when(bitonicSequenceCacheDAO.findBySizeAndLowerBoundaryAndUpperBoundary(5, 3, 3)).thenReturn(None)
    when(bitonicSequenceDAO.save(entityToSave)).thenReturn(entity)
    when(bitonicSequenceCacheDAO.save(cacheEntityToSave)).thenReturn(cacheEntity)

    val response = bitonicService.createBitonicSequence(request)

    assertNotNull(response)
    assertNotNull(response.id)
    assertEquals(5, response.size)
    assertEquals(3, response.lowerBoundary)
    assertEquals(3, response.upperBoundary)
    assertEquals(Seq(-1), response.sequence)
  }

  @Test
  def shouldReturnCachedBitonicSequence(): Unit = {
    val request = createRequest()
    val cacheEntity = createCacheEntity()
    val bitonicService = createBitonicService()

    when(bitonicSequenceCacheDAO.findBySizeAndLowerBoundaryAndUpperBoundary(5, 3, 10)).thenReturn(Option(cacheEntity))

    val response = bitonicService.createBitonicSequence(request)

    assertNotNull(response)
    assertNotNull(response.id)
    assertEquals(5, response.size)
    assertEquals(3, response.lowerBoundary)
    assertEquals(10, response.upperBoundary)
    assertEquals(Seq(9, 10, 9, 8, 7), response.sequence)
  }

  private def createBitonicService(): BitonicService = {
    BitonicService(bitonicSequenceDAO = bitonicSequenceDAO, bitonicSequenceCacheDAO = bitonicSequenceCacheDAO)
  }

  private def createRequest(size: Int = 5, lowerBoundary: Int = 3, upperBoundary: Int = 10): BitonicRequest = {
    BitonicRequest(
      size = size,
      lowerBoundary = lowerBoundary,
      upperBoundary = upperBoundary
    )
  }

  private def createEntity(id: UUID = UUID.randomUUID(),
                           size: Int = 5,
                           lowerBoundary: Int = 3,
                           upperBoundary: Int = 10,
                           sequence: Seq[Int] = Seq(9,10,9,8,7)) = {
    BitonicSequenceEntity.fromSeq(
      id = id,
      size = size,
      lowerBoundary = lowerBoundary,
      upperBoundary = upperBoundary,
      sequence = sequence
    )
  }

  private def createCacheEntity(id: UUID = UUID.randomUUID(),
                                size: Int = 5,
                                lowerBoundary: Int = 3,
                                upperBoundary: Int = 10,
                                sequence: Seq[Int] = Seq(9, 10, 9, 8, 7)) = {
    BitonicSequenceCacheEntity.fromSeq(
      id = id,
      size = size,
      lowerBoundary = lowerBoundary,
      upperBoundary = upperBoundary,
      sequence = sequence
    )
  }

}
