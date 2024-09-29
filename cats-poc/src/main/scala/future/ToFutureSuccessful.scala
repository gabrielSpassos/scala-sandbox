package future

import scala.concurrent.Future

implicit class ToFutureSuccessful[T](obj: T) {
  def asFuture: Future[T] = Future.successful(obj)
}
