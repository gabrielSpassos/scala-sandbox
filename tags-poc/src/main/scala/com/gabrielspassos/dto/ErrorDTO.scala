package com.gabrielspassos.dto

case class BadRequestErrorDTO(message: String) extends ErrorDTO(400, message)

case class NotFoundErrorDTO(message: String) extends ErrorDTO(404, message)

case class InternalErrorDTO(message: String) extends ErrorDTO(500, message)

class ErrorDTO(status: Int, message: String) {
  def getStatus: Int = status
  def getMessage: String = message
}
