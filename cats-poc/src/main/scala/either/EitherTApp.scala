package either

import cats.data.EitherT

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object EitherTApp extends App {

  def parseDouble(s: String): Either[String, Double] =
    Try(s.toDouble).map(Right(_)).getOrElse(Left(s"$s is not a number"))

  def divide(a: Double, b: Double): Either[String, Double] =
    Either.cond(b != 0, a / b, "Cannot divide by zero")

  def parseDoubleAsync(s: String): Future[Either[String, Double]] =
    Future.successful(parseDouble(s))

  def divideAsync(a: Double, b: Double): Future[Either[String, Double]] =
    Future.successful(divide(a, b))

  def divisionProgramAsync(inputA: String, inputB: String): EitherT[Future, String, Double] =
    for {
      a <- EitherT(parseDoubleAsync(inputA))
      b <- EitherT(parseDoubleAsync(inputB))
      result <- EitherT(divideAsync(a, b))
    } yield result

  divisionProgramAsync("4", "2").value.onComplete {
    case Success(value) => println(value) //Future[Either[String, Double]] = Future(Success(Right(2.0)))
    case Failure(exception) => println(s"Failed $exception")
  }

  divisionProgramAsync("a", "b").value.onComplete {
    case Success(value) => println(value) //Future[Either[String, Double]] = Future(Success(Left(a is not a number)))
    case Failure(exception) => println(s"Failed $exception")
  }

}
