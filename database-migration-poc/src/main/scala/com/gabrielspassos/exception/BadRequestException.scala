package com.gabrielspassos.exception

class BadRequestException(message: String) extends HttpException(message, httpStatus = 400)
