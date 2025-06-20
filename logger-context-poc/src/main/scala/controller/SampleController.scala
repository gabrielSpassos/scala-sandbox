package controller

import logger.ContextLogger
import service.SampleService

class SampleController(private val service: SampleService, private val contextLogger: ContextLogger) {

  private val logger = contextLogger.ofClass(getClass)
  
  def getData: String = {
    logger.info("Starting to get data")
    val data = service.fetchData()
    logger.info(s"Received data: $data")
    data
  }
  
  def getIds: Seq[Int] = {
    logger.info("Start to get ids")
    val ids = service.getIds
    logger.info(s"Received ids: $ids")
    ids
  }

}
