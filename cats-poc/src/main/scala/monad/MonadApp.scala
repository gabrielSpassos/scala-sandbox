package monad

import cats.implicits.*

// Monad provides a way to chain operations that return wrapped values.
object MonadApp extends App {

  private final case class Person(name: String, lastName: String, birthYear: Int, height: Double)

  private val value: Option[Person] = Some(Person("Gabriel", "Passos", 1997, 1.84))
  private val result: Option[String] = value.flatMap(p => Some(p.name ++ " " ++ p.lastName))

  println(result) // Some(Gabriel Passos)
  
}
