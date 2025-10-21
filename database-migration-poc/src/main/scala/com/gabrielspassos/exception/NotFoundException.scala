package com.gabrielspassos.exception

class NotFoundException(message: String) extends HttpException(message, httpStatus = 404)
