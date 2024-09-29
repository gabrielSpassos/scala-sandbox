package eq

import cats.instances.int.*
import cats.syntax.eq.*
import cats.syntax.option.*

class CatsEqSyntaxTest extends munit.FunSuite {

  test("should return true") {
    assert(123 === 123)
  }

  test("should be different") {
    assert(123 =!= 456)
  }

  test("should return false for option") {
    val result = 1.some === none[Int]

    assertEquals(result, false)
  }

//  test("should not compile") {
//    assert(123 === "123")
//  }

}
