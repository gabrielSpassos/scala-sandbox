case class Rectangle(width: Double, length: Double)

case class Circle(radius: Double)

object AreaInstances {
  implicit val rectangleArea: Area[Rectangle] = new Area[Rectangle] {
    override def area(a: Rectangle): Double = a.width * a.length
  }
  
  implicit val circleArea: Area[Circle] = (a: Circle) => Math.PI * (a.radius * a.radius)
}