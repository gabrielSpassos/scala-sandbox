package com.gabrielspassos.controller.v1.response

import com.gabrielspassos.contracts.v1.response.BaseApiResponse
import org.springframework.http.HttpStatus

class GenericApiResponse[T](
 val body: T,
 val statusCode: Int,
 val message: String,
) extends BaseApiResponse[T] {

  def this() = this(null.asInstanceOf[T], 0, null)

  override def getBody: T = body

  override def getStatusCode: Int = statusCode

  override def getMessage: String = message

}

object GenericApiResponse {
  def apply[T](body: T, statusCode: Int, message: String): GenericApiResponse[T] = {
    new GenericApiResponse(body, statusCode, message)
  }

  def ok[T](body: T): GenericApiResponse[T] = {
    apply(body, 200, "OK")
  }

  def notFound[T](body: T): GenericApiResponse[T] = {
    apply(body, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase)
  }

}
