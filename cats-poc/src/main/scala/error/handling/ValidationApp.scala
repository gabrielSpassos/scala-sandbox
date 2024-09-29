package error.handling

import cats.data.Validated
import cats.data.Validated.{Invalid, Valid}

// Validated accumulates errors instead of failing fast, which is useful for validation.
object ValidationApp extends App {

  private val results = List(-1, 2, 3, -4).map(validatePositive)
  println(results)

  val partition = results.partition(p => p.isValid)

  println(s"Valid results: ${partition._1}")
  println(s"Invalid results: ${partition._2}")
}

def validatePositive(n: Int): Validated[String, Int] =
  if (n > 0) Valid(n) else Invalid("Must be positive")
