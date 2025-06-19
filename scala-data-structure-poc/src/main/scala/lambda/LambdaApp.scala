package lambda

object LambdaApp extends App {

  println("Lambda POC")

  // Example generics HTTP call simulation
  private val httpCall1 = () => HttpRespone(200, "OK")
  private val getHttpStatusCode1: HttpRespone => Int = _.status
  private val getResponseBody1: HttpRespone => String = _.body

  private val httpCall2 = () => WrapperHttpResponse(httpResponse = Response(httpStatus = 404, body = "Not Found"))
  private val getHttpStatusCode2: WrapperHttpResponse => Int = _.httpResponse.httpStatus
  private val getResponseBody2: WrapperHttpResponse => String = _.httpResponse.body

  private val httpCall3 = () => JHttpResponse(500, "Internal Server Error")
  private val getHttpStatusCode3: JHttpResponse => Int = _.getStatusCode
  private val getResponseBody3: JHttpResponse => String = _.getResponseBody

  // Sending HTTP calls
  private val httpResponse1 = sendHttCall(httpCall1, getHttpStatusCode1, getResponseBody1)
  println(s"Http Response 1: $httpResponse1")

  private val httpResponse2 = sendHttCall(httpCall2, getHttpStatusCode2, getResponseBody2)
  println(s"Http Response 2: $httpResponse2")

  private val httpResponse3 = sendHttCall(httpCall3, getHttpStatusCode3, getResponseBody3)
  println(s"Http Response 3: $httpResponse3")

  private def sendHttCall[Resp](httpCall: () => Resp,
                                getHttpStatusCode: Resp => Int,
                                getResponseBody: Resp => String): Resp = {
    val httpResponse = httpCall()

    val httpStatusCode = getHttpStatusCode(httpResponse)
    httpStatusCode match {
      case code if code >= 200 && code < 300 =>
        println("Success")
      case code if code >= 400 && code < 500 =>
        println("Client error")
      case code if code >= 500 =>
        println("Server error")
      case _ =>
        println("Other")
    }

    val responseBody = getResponseBody(httpResponse)

    println(s"HTTP Status Code: $httpStatusCode, Response Body: $responseBody")

    httpResponse
  }

}
