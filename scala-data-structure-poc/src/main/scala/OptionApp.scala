case class User(name: String, age: Int)

object OptionApp extends App {

  println("Option POC")

  val option: Option[User] = None
  val result1 = option.forall(_.name.isBlank)
  val result2 = option.exists(_.name.isBlank)

  println(result1)
  println(result2)
}
