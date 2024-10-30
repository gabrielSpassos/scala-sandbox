import Logger.{info, warn}

import scala.math.{Pi, pow}

case class Circle(radius: Double) {
  import Circle.calculateArea

  def area: Double = calculateArea(radius)
}

object Circle {
  private def calculateArea(radius: Double): Double = Pi * pow(radius, 2.0)
}

case class CompanionEmail(username: String, domainName: String)

object CompanionEmail {
  def fromString(emailString: String): Option[CompanionEmail] = {
    emailString.split('@') match {
      case Array(a, b) => Some(new CompanionEmail(a, b))
      case _ => None
    }
  }
}

object CompanionObjectsMain extends App {
  private val circle = Circle(5.0)

  info(s"Circle area ${circle.area}")

  private val optionEmail = CompanionEmail.fromString("gabriel.passos@test.com")
  optionEmail match {
    case Some(email) => info(
      s"""Registered an email
         |username: ${email.username}
         |domainName: ${email.domainName}
      """.stripMargin
    )
    case None => warn("Could not parse email")
  }
}