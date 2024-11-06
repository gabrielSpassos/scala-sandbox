import Logger.{info, warn}

case class MyError(msg: String)

object EitherSample extends App {
  println("Either Sample")

  private val e: Either[String, Int] = Right(5)
  e match {
    case Right(x) => info(s"Value is $x")
    case Left(_) => warn("Either should be right")
  }
  info(s"Either map value: ${e.map(_ + 10)}")

  private val eitherEquals: Either[Int, Int] = Right(6)
  info(s"Either with both types equals, value: ${eitherEquals.merge}")

  private val eitherWithError: Either[String, Int] = Left("Some error")
  private val defaultValue = 10

  info(s"Either with error and default value (getOrElse): ${eitherWithError.getOrElse(defaultValue)}")
  info(s"Either with error and default value (fold): ${eitherWithError.fold(_ => defaultValue, identity)}")

  warn(s"Either with error (fold): ${eitherWithError.fold(error => MyError(msg = error), identity)}")
}
