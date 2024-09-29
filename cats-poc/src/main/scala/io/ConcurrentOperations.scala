package io

import cats.effect.unsafe.IORuntime
import cats.effect.{Deferred, IO, IOApp, Ref}
import cats.implicits.catsSyntaxTuple2Parallel

// Ref allows for safe mutable state.
object RefOperations extends IOApp.Simple {

  override def run: IO[Unit] =
    for {
      ref <- Ref[IO].of(10) // Create a Ref with an initial value
      _ <- ref.update(_ + 1) // Increment the value
      value <- ref.get // Get the current value
    } yield println(value) // Outputs: 11

}

// Deferred allows you to create a value that can be set later.
object DeferredOperations extends IOApp.Simple {

  val deferred: IO[Deferred[IO, Int]] = Deferred[IO, Int]

  override def run: IO[Unit] =
    for {
      d <- deferred
      _ <- d.complete(17) // Set the value
      value <- d.get // Get the value
    } yield println(value) // Outputs: 17

}

object ParMapN extends App {

  // Create an implicit IORuntime
  implicit val runtime: IORuntime = IORuntime.global

  val io1: IO[Int] = IO {
    Thread.sleep(1000)
    8
  }

  val io2: IO[Int] = IO {
    Thread.sleep(1000)
    7
  }

  private val combined: IO[Int] = (io1, io2).parMapN(_ * _)

  println(combined.unsafeRunSync()) // Outputs: 56 after ~1 second

}
