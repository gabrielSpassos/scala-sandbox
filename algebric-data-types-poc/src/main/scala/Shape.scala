sealed trait Shape

case class Circle(radius: Double) extends Shape

case class Rectangle(width: Double, height: Double) extends Shape


object ShapeApp extends App {
  private val circle = Circle(2.5)
  private val rectangle = Rectangle(5, 2.5)

  println(s"Circle $circle area: ${area(circle)}")
  println(s"Rectangle $rectangle area: ${area(rectangle)}")

  private def area(shape: Shape): Double = shape match {
    case Circle(r) => Math.PI * r * r
    case Rectangle(w, h) => w * h
  }
}