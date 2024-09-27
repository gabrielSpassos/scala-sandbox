package semigroups

import cats.instances.int.*
import cats.kernel.Semigroup

class CustomSemigroupTest extends munit.FunSuite {

  implicit val multiplySemigroup: Semigroup[Int] = (x: Int, y: Int) => x * y

  implicit val divideSemigroup: Semigroup[Double] = (x: Double, y: Double) => x / y

  test("should combine int values") {
    val result = Semigroup[Int].combine(7, 2)

    assertEquals(result, 14)
  }

  test("should combine double values") {
    val result = Semigroup[Double].combine(100.0, 2.0)

    assertEquals(result, 50.0)
  }

}
