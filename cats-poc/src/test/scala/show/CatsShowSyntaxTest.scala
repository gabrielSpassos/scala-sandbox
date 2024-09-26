package show

import cats.syntax.show.*

class CatsShowSyntaxTest extends munit.FunSuite {

  test("should return int as string") {
    assertEquals("439", 439.show)
  }

  test("should return boolean as string") {
    assertEquals("true", true.show)
  }

  test("should return double as sting") {
    assertEquals("2.7", 2.7.show)
  }

  test("should return string as string") {
    assertEquals("gabriel", "gabriel".show)
  }
}
