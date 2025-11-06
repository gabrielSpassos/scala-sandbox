package com.gabrielspassos.controller

import com.gabrielspassos.contracts.v1.response.ErrorResponse
import com.gabrielspassos.exception.HttpException
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler, ResponseBody}

@ControllerAdvice
class GlobalErrorHandler {

  @ResponseBody
  @ExceptionHandler(Array(classOf[HttpException]))
  def handleAllExceptions(ex: HttpException): ResponseEntity[ErrorResponse] = {
    val code = ex.getHttpStatus
    val errorResponse = ErrorResponse()
    errorResponse.setCode(code.toString)
    errorResponse.setMessage(ex.getMessage)
    ResponseEntity.status(code).body(errorResponse)
  }
  
  @ResponseBody
  @ExceptionHandler(Array(classOf[Exception]))
  def handleAllExceptions(ex: Exception): ResponseEntity[ErrorResponse] = {
    val code = HttpStatus.INTERNAL_SERVER_ERROR.value()
    val errorResponse = ErrorResponse()
    errorResponse.setCode(code.toString)
    errorResponse.setMessage("Internal Server Error")
    ResponseEntity.status(code).body(errorResponse)
  }
}
