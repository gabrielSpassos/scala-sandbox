package functor

import cats.*
import cats.implicits.*

// A Functor allows you to map over a wrapped value (like Option, List, etc.).
object FunctorApp extends App {

  private val numbers: List[Int] = List(1, 2, 3)
  private val incremented: List[Int] = numbers.map(_ + 1) // Using List's built-in map
  private val incrementedCats: List[Int] = Functor[List].map(numbers)(_ + 1) // Using Cats Functor

  println(incremented) // List(2, 3, 4)
  println(incrementedCats) // List(2, 3, 4)

}

// Applicative extends Functor, allowing you to apply functions that are wrapped in a context.
object ApplicativeApp extends App {

  private val f: Option[Int => Int] = Some(_ * 2)
  private val value: Option[Int] = Some(2)
  private val result: Option[Int] = Applicative[Option].ap(f)(value)
  private val result2: Option[Int] = Applicative[Option].ap(f)(result)

  println(result) // Some(4)
  println(result2) // Some(8)

}