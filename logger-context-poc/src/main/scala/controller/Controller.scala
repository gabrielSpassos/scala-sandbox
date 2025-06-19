package controller

import logger.ContextLogger
import service.Service

class Controller(private val service: Service, private val contextLogger: ContextLogger) {

  private val logger = contextLogger.ofClass(getClass)
  
  def getData: String = {
    logger.info("Starting to get data")
    val data = service.fetchData()
    logger.info(s"Received data: $data")
    data
  }

}
