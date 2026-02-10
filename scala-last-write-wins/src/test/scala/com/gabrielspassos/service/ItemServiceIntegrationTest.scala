package com.gabrielspassos.service

import com.gabrielspassos.entity.ItemEntity
import com.gabrielspassos.{Application, BaseIntegrationTest}
import org.junit.jupiter.api.{Test, TestInstance}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan

import java.time.Instant
import java.util.UUID
import scala.concurrent.duration.*
import scala.concurrent.{Await, ExecutionContext, Future}

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[Application])
)
@EnableAutoConfiguration
@ComponentScan(Array("com.*"))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemServiceIntegrationTest @Autowired()(
  private val itemService: ItemService,
) extends BaseIntegrationTest {

  given ExecutionContext = ExecutionContext.global
  
  @Test
  def shouldFindCardsByInstitutionNameSuccessfully(): Unit = {
    val externalId = UUID.randomUUID().toString
    val updatedAt = Instant.now()

    val older = ItemEntity(id = null, externalId = externalId, value = "OLD", updatedAt = updatedAt)
    val newer = ItemEntity(id = null, externalId = externalId, value = "NEW", updatedAt = updatedAt.plusSeconds(5))

    val f1 = Future(itemService.upsert(older))
    val f2 = Future(itemService.upsert(newer))

    Await.result(Future.sequence(List(f1, f2)), 5.seconds)

    val result = itemService.findByExternalId(externalId).get

    assert(result.value == "NEW")
  }

}
