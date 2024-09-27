package semigroups

import cats.instances.int.*
import cats.kernel.Semigroup

class SemigroupTest extends munit.FunSuite {

  test("should combine int values") {
    val result = Semigroup[Int].combine(7, 2)

    assertEquals(result, 9)
  }

  test("should combine double values") {
    val result = Semigroup[Double].combine(9.5, 2.3)

    assertEquals(result, 11.8)
  }

  test("should combine string values") {
    val result = Semigroup[String].combine("foo", "bar")

    assertEquals(result, "foobar")
  }

}
