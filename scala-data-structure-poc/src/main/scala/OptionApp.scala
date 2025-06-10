case class User(name: String, age: Int)

object OptionApp extends App {

  println("Option POC")

  private val option: Option[User] = None
  private val result1 = option.forall(_.name.isBlank)
  private val result2 = option.exists(_.name.isBlank)

  println(result1)
  println(result2)

  private val list1 = List(Person(name = "gabriel", account = MyOptionPocAccount(number = "123456")))
  private val list2 = List.empty[Person]
  private val list3 = List(Person(name = "jose", account = null))
  private val list4 = List(Person(name = "maria", account = MyOptionPocAccount(number = null)))
  private val list5 = null

  println(getAccountNumbers(list1))
  println(getAccountNumbers(list2))
  println(getAccountNumbers(list3))
  println(getAccountNumbers(list4))
  println(getAccountNumbers(list5))

}

private def getAccountNumbers(persons: List[Person]): List[String] =
  Option(persons).toList.flatten.flatMap { person =>
    for
      account <- Option(person.account)
      number  <- Option(account.number)
    yield number
  }
case class Person(name: String, account: MyOptionPocAccount)

case class MyOptionPocAccount(number: String)
