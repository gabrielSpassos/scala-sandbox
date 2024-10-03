package functor

import cats.*
import cats.data.EitherT
import cats.implicits.*

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Random, Success, Try}

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

import cats.implicits.*

object EitherExample extends App {
  private def withdrawal(balance: Double, amount: Double): Either[String, Double] =
    if (amount > balance) Left(s"Cannot withdrawal $amount")
    else Right(balance - amount)

  private val successResult = (Right(100.0): Either[String, Double], Right(20.0): Either[String, Double])
    .flatMapN(withdrawal)
  private val failureResult = (Right(100.0): Either[String, Double], Right(200.0): Either[String, Double])
    .flatMapN(withdrawal)

  println(successResult) // Output: Right(80.0)
  println(failureResult) // Output: Left(Cannot withdrawal 200.0)
}

object EitherTApp extends App {

  def getBalanceByUser(userId: String): Either[String, Double] =
    if (userId == null) Left(s"Cannot found user")
    else Right(100.0)

  def withdrawal(balance: Double, amount: Double): Either[String, Double] =
    if (amount > balance) Left(s"Cannot withdrawal $amount")
    else Right(balance - amount)

  def withdrawalProgramAsync(userId: String, amount: Double): EitherT[Future, String, Double] =
    for {
      balance <- EitherT(Future.successful(getBalanceByUser(userId)))
      result <- EitherT(Future.successful(withdrawal(balance, amount)))
    } yield result

  withdrawalProgramAsync("userId1", 20.0).value.onComplete {
    case Success(value) => println(value) // Right(80.0)
    case Failure(exception) => println(s"Failed $exception")
  }

  withdrawalProgramAsync("userId2", 150.0).value.onComplete {
    case Success(value) => println(value) // Left(Cannot withdrawal 150.0)
    case Failure(exception) => println(s"Failed $exception")
  }

  withdrawalProgramAsync(null, 50.0).value.onComplete {
    case Success(value) => println(value) // Left(Cannot found user)
    case Failure(exception) => println(s"Failed $exception")
  }

}

object FutureApp extends App {
  val future1 = {
    // Initialize Random with a fixed seed:
    val r = new Random(0L)
    // nextInt has the side-effect of moving to
    // the next random number in the sequence:
    val x = Future(r.nextInt)
    for {
      a <- x
      b <- x
    } yield (a, b)
  }

  val future2 = {
    val r = new Random(0L)
    for {
      a <- Future(r.nextInt)
      b <- Future(r.nextInt)
    } yield (a, b)
  }

  val result1 = Await.result(future1, 1.second)
  // result1: (Int, Int) = (-1155484576, -1155484576)
  println(result1)
  val result2 = Await.result(future2, 1.second)
  // result2: (Int, Int) = (-1155484576, -723955400)
  println(result2)
}