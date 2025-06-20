package controller

import client.HttpClient
import org.scalatest.flatspec.AnyFlatSpec
import controller.SampleController
import service.SampleService
import logger.ContextLogger
import repository.SampleRepository

class SampleControllerIntegrationTest extends AnyFlatSpec {

  "getData" should "fetch data" in {
    val controller = createController()

    val data = controller.getData

    assert(null != data)
    assert(data.nonEmpty)
  }

  "getIds" should "return ids" in {
    val controller = createController()

    val ids = controller.getIds

    assert(null != ids)
    assert(ids.nonEmpty)
    assert(5 == ids.size)
  }

  private def createController(): SampleController = {
    val contextLogger = ContextLogger()
    val httpClient = HttpClient(contextLogger)
    val sampleRepository = SampleRepository(contextLogger)
    val service = SampleService(httpClient, sampleRepository, contextLogger)
    SampleController(service, contextLogger)
  }
}