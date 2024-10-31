object OptionSample extends App {

  println("Option Sample")

  private val option1: Option[Int] = None
  println(option1)
  if (option1.isDefined) {
    println("Option 1 is defined")
  }

  if (option1.isEmpty) {
    println("Option 1 is empty")
  }

  if (option1.nonEmpty) {
    println("Option 1 is NOT empty")
  }

  private val value1 = if (option1.isDefined) {
    option1.get
  } else {
    0
  }
  println(s"Option 1 value $value1")

  private val option2 = Some(2)
  println(option2)

  if (option2.isDefined) {
    println("Option 2 is defined")
  }

  if (option2.isEmpty) {
    println("Option 2 is empty")
  }

  if (option2.nonEmpty) {
    println("Option 2 is NOT empty")
  }

  private val value2 = option2 match {
    case Some(n) => n
    case _ => 0
  }

  println(s"Option 2 value $value2")

}
