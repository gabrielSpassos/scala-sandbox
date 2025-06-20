package controller

import client.HttpClient
import org.scalatest.flatspec.AnyFlatSpec
import controller.SampleController
import service.SampleService
import logger.ContextLogger

class SampleControllerIntegrationTest extends AnyFlatSpec {

  "getData" should "fetch data" in {
    val controller = createController()

    val data = controller.getData

    assert(null != data)
    assert(data.nonEmpty)
  }

  private def createController(): SampleController = {
    val contextLogger = ContextLogger()
    val httpClient = HttpClient(contextLogger)
    val service = SampleService(httpClient, contextLogger)
    SampleController(service, contextLogger)
  }
}