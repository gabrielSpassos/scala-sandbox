case class User(name: String, age: Int)

object ForComprehensionsMain extends App {
  println("For Comprehensions")

  private val users = List(
    User("Gabriel", 27),
    User("Josh", 34),
    User("Mary", 29),
    User("Mark", 19)
  )

  private val twentySomethings =
    for (user <- users if user.age >= 20 && user.age < 30)
      yield user.name

  twentySomethings.foreach(println)
}
