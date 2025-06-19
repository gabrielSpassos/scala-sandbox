package lambda

case class WrapperHttpResponse(httpResponse: Response)

case class Response(httpStatus: Int, body: String)
