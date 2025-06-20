package service

import client.HttpClient
import logger.ContextLogger

class SampleService(private val httpClient: HttpClient, private val contextLogger: ContextLogger) {

  private val logger = contextLogger.ofClass(getClass)

  def fetchData(): String = {
    val url = "http://example.com/data"
    logger.info(s"Starting fetchData from $url")
    val response = httpClient.get(url)
    logger.info(s"Received response: $response")
    response
  }

}
