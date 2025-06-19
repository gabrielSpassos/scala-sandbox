package client

import logger.ContextLogger

class HttpClient(private val contextLogger: ContextLogger) {

  private val logger = contextLogger.ofClass(getClass)

  def get(url: String): String = {
    logger.info(s"Sending GET request to $url")
    // Simulate an HTTP GET request
    s"Response from GET request from $url"
  }

}
