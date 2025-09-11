package com.gabrielspassos.service

import com.gabrielspassos.controller.v1.request.BitonicRequest
import com.gabrielspassos.dao.BitonicSequenceDAO
import com.gabrielspassos.entity.BitonicSequenceEntity
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

  @Test
  def shouldCreateBitonicSequence(): Unit = {
    val request = createRequest()
    val entity = createEntity()
    val entityToSave = entity.copy(id = null)
    val bitonicService = createBitonicService()

    when(bitonicSequenceDAO.save(entityToSave)).thenReturn(entity)

    val response = bitonicService.createBitonicSequence(request)

    assertNotNull(response)
    assertNotNull(response.id)
    assertEquals(5, response.size)
    assertEquals(3, response.lowerBoundary)
    assertEquals(10, response.upperBoundary)
    assertEquals("9,10,9,8,7", response.sequence)
  }

  @Test
  def shouldCreateInvalidBitonicSequence(): Unit = {
    val request = createRequest(size = 5, lowerBoundary = 3, upperBoundary = 3)
    val entity = createEntity(size = 5, lowerBoundary = 3, upperBoundary = 3, sequence = Seq(-1))
    val entityToSave = entity.copy(id = null)
    val bitonicService = createBitonicService()

    when(bitonicSequenceDAO.save(entityToSave)).thenReturn(entity)

    val response = bitonicService.createBitonicSequence(request)

    assertNotNull(response)
    assertNotNull(response.id)
    assertEquals(5, response.size)
    assertEquals(3, response.lowerBoundary)
    assertEquals(3, response.upperBoundary)
    assertEquals("-1", response.sequence)
  }

  private def createBitonicService(): BitonicService = {
    BitonicService(bitonicSequenceDAO)
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

}
