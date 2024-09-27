package semigroups

import cats.instances.int.*
import cats.kernel.Semigroup

object SemigroupImpl extends App {

  private val onePlusTwo = Semigroup[Int].combine(7, 2)

  println(onePlusTwo)
}
