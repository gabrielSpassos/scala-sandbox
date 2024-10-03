package fiber

import cats.effect.*
import cats.implicits.toFunctorOps

import java.util.UUID
import scala.concurrent.duration.*
import scala.util.Random

object FiberApp extends IOApp.Simple {

  override def run: IO[Unit] =
    val task = for {
      _ <- IO.println("Task started")
      _ <- IO.sleep(1.second)
      _ <- IO.println("Task completed")
    } yield ()

    for {
      fiber <- task.start
      _ <- IO.println("Hello from main fiber")
      //we wait approx. 1s till forked fiber completes
      _ <- fiber.join
    } yield ()

}

object FiberWithCancelApp extends IOApp.Simple {

  override def run: IO[Unit] =
    val task = for {
      _ <- IO.println("Task started")
      _ <- IO.sleep(5.seconds)
      _ <- IO.println("Task completed")
    } yield ()

    for {
      fiber <- task.onCancel(IO.println("Task canceled!")).start
      _ <- IO.sleep(1.second)
      //fiber would complete after 5s, but is canceled after 1s
      _ <- fiber.cancel
    } yield ()

}

object FiberPatternMatchingJoinOutcome extends IOApp.Simple {
  val fallback = IO.sleep(390.millis)
    .onCancel(IO.println("Fallback Task has timed out!"))
    .timeout(400.millis)
    *> IO.pure("Fallback task!")

  val task = IO.sleep(350.millis)
    .onCancel(IO.println("Task has timed out!"))
    .timeoutTo(300.millis, fallback)
    *> IO.pure("Root task!")

  override def run: IO[Unit] =
    for {
      fiber <- task.start
      _ <- fiber.join.flatMap {
        case Outcome.Succeeded(value) => value.flatMap(
          v => IO.println(s"Computed value: $v")
        )
        case Outcome.Errored(e) => IO.println(s"Error: $e")
        case Outcome.Canceled() => IO.println("Task was canceled!")
      }
    } yield ()
}

object FiberBackgroundTask extends IOApp.Simple {
  val task = for {
    time <- IO.realTimeInstant
    _ <- IO.println(f"Current date and time is $time")
    _ <- IO.sleep(1.second)
  } yield ()

  val backgroundTask: Resource[IO, Unit] = task
    .onCancel(IO.println("Closing the background task"))
    .background
    .void

  override def run: IO[Unit] =
    backgroundTask.surround {
      for {
        _ <- IO.sleep(2.seconds)
        _ <- IO.println("Bye") //after foreground task is complete
        //the background fiber will be canceled
      } yield ()
    }
}

object FiberConcurrencyApp extends IOApp.Simple {

  val raced: IO[Either[String, String]] = IO.race(
    IO("Faster").delayBy(5.seconds),
    IO("Slower").delayBy(10.seconds)
  )

  val racedPair = IO.racePair(IO(10).delayBy(3.seconds), IO(5).delayBy(2.seconds)).flatMap {
    case Left((resL, fibR)) =>
      fibR.cancel *> IO(s"Left fiber outcome: $resL $fibR")
    case Right((fibL, resR)) =>
      fibL.cancel *> IO(s"Right fiber outcome: $resR $fibL")
  }

  override def run: IO[Unit] =
    for {
      fiber <- racedPair.start
      _ <- fiber.join.flatMap {
        case Outcome.Succeeded(value) => value.flatMap(
          v => IO.println(s"Computed value: $v")
        )
        case Outcome.Errored(e) => IO.println(s"Error: $e")
        case Outcome.Canceled() => IO.println("Task was canceled!")
      }
    } yield ()

}

object FiberConcurrencyApp2 extends IOApp.Simple {

  val userId = UUID.randomUUID().toString
  val random = Random()

  def updateUser(userId: String): Either[String, String] =
    if (userId == null) Left(s"Cannot found user")
    else {
      val sleep = random.between(100, 1000)
      println(s"Sleep on update user $userId: $sleep")
      Thread.sleep(sleep) //blocking IO
      Right(s"Updated User $userId")
    }

  def updateUserAddress(userId: String): Either[String, String] =
    if (userId == null) Left(s"Cannot found user")
    else {
      val sleep = random.between(100, 1000)
      println(s"Sleep on update user $userId address: $sleep")
      Thread.sleep(sleep) //blocking IO
      Right(s"Updated User $userId Address")
    }

  def concurrentIO(userId: String) = for {
    fiber1 <- IO.pure(updateUser(userId)).start // Start a fiber for the first IO
    fiber2 <- IO.pure(updateUserAddress(userId)).start // Start another fiber
    result1 <- fiber1.join // Wait for the first fiber to complete
    result2 <- fiber2.join // Wait for the second fiber to complete
  } yield (result1, result2)
  
  override def run: IO[Unit] =
    for {
      (result1, result2) <- concurrentIO(userId)
      _ <- IO.println(s"Result1: $result1, Result2: $result2")
      (result1, result2) <- concurrentIO(null)
      _ <- IO.println(s"Result1: $result1, Result2: $result2")
    } yield ()
}