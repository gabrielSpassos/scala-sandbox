package controller

import client.HttpClient
import org.scalatest.flatspec.AnyFlatSpec
import controller.Controller
import service.Service
import logger.ContextLogger

class ControllerIntegrationTest extends AnyFlatSpec {

  "getData" should "fetch data" in {
    val controller = createController()

    val data = controller.getData

    assert(null != data)
    assert(data.nonEmpty)
  }

  private def createController(): Controller = {
    val contextLogger = ContextLogger()
    val httpClient = HttpClient(contextLogger)
    val service = Service(httpClient, contextLogger)
    Controller(service, contextLogger)
  }
}