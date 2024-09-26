import AreaInstances._

class ShapeAreaTest extends munit.FunSuite {
  val rectangle: Rectangle = Rectangle(2, 4)
  val circle: Circle = Circle(3.5)

  test("should return area of rectangle") {
    val area = ShapeArea.areaOf(rectangle)
    assertEquals(8.0, area)
  }

  test("should return area of circle") {
    val area = ShapeArea.areaOf(circle)
    assertEquals(38.48451000647496, area)
  }
}
