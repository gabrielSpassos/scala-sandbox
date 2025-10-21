package com.gabrielspassos.exception

class HttpException(
  message: String,
  httpStatus: Int
) extends RuntimeException(message)
