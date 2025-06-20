package repository

import logger.ContextLogger

class SampleRepository(private val contextLogger: ContextLogger) {

  private val logger = contextLogger.ofClass(getClass)

  def getIds: Seq[Int] = {
    logger.info("Fetching ids from repository")
    Seq(1, 2, 3, 4, 5)
  }
  
}
