package eq

import cats.Eq
import cats.instances.int.*
import cats.instances.option.*

class CatsEqTest extends munit.FunSuite {

  test("should filter list") {
    val eqInt = Eq[Int]
    val result = List(1, 2, 3).map(Option(_)).filter(item => eqInt.eqv(1, item.get))

    assertEquals(1, result.size)
  }

  test("should compare option") {
    val eqOption = Eq[Option[Int]]

    assert(eqOption.eqv(Some(1), Some(1)))
  }

}
