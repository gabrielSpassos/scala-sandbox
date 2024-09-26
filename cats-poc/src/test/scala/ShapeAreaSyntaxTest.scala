import AreaInstances.*
import ShapeAreaSyntax.*

class ShapeAreaSyntaxTest extends munit.FunSuite {

  test("should return area of rectangle") {
    val area = Rectangle(2, 4).areaOf
    assertEquals(8.0, area)
  }

  test("should return area of circle") {
    val area = Circle(3.5).areaOf
    assertEquals(38.48451000647496, area)
  }
}
