import cats.Show
import cats.instances.int.*
import cats.instances.string.*

class CatsShowTest extends munit.FunSuite {

  test("should return int show") {
    val showInt: Show[Int] = Show.apply[Int]

    assert(showInt != null)

    val intAsString = showInt.show(439)

    assertEquals("439", intAsString)
  }

  test("should return boolean show") {
    val showBool: Show[Boolean] = Show.apply[Boolean]

    assert(showBool != null)

    val boolAsString = showBool.show(true)

    assertEquals("true", boolAsString)
  }

  test("should return double show") {
    val showDouble: Show[Double] = Show.apply[Double]

    assert(showDouble != null)

    val doubleAsString = showDouble.show(2.7)

    assertEquals("2.7", doubleAsString)
  }

  test("should return string show") {
    val showString: Show[String] = Show.apply[String]

    assert(showString != null)

    val stringAsString = showString.show("gabriel")

    assertEquals("gabriel", stringAsString)
  }
}
