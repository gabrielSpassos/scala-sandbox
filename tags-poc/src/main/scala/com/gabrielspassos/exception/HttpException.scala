package com.gabrielspassos.exception

class HttpException(
  message: String,
  httpStatus: Int
) extends RuntimeException(message) {

  def getHttpStatus: Int = httpStatus
  override def getMessage: String = message
  
}
