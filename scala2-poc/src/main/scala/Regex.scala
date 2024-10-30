import Logger.{info, warn}

import scala.util.matching.Regex

object RegexMain extends App {

  private val numberPattern: Regex = "[0-9]".r

  verifyPassword("passwordWithoutNumbers")
  verifyPassword("passwordWith12345")

  private def verifyPassword(password: String) = {
    numberPattern.findFirstMatchIn(password) match {
      case Some(_) => info("Password OK")
      case None => warn("Password missing numbers")
    }
  }

}
