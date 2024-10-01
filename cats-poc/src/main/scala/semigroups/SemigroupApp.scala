package semigroups

import cats.Semigroup
import cats.syntax.apply.* // for tupled and mapN

object SemigroupApp extends App {

  private val optionTuple1 = (Option(123), Option("abc")).tupled
  println(optionTuple1)

  private val optionTuple2 = (Option(123), Option("abc"), Option(true), Option(2.5)).tupled
  println(optionTuple2)

  val map1 = Map("foo" -> Option(1), "bar" -> Option(1), "tar" -> None, "cats" -> None)
  val map2 = Map("foo" -> Option(2), "cats" -> Option(4))
  val mapSemigroupCombine = Semigroup[Map[String, Option[Int]]].combine(map1, map2)
  println(mapSemigroupCombine)

  private final case class Person(name: String, lastName: String,
                                  birthYear: Int, height: Double)

  private val personMapResult = (
    Option("Gabriel"),
    Option("Passos"),
    Option(1997),
    Option(1.84)
  ).mapN(Person.apply)
  println(personMapResult)

  private val sum: (Int, Int, Int) => Int = (a, b, c) => a + b + c
  val result = (Option(1), Option(2), Option(3)).mapN(sum)
  println(result)

}
