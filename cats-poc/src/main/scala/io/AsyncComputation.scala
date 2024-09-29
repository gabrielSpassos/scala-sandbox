package io

import cats.effect.IO
import cats.effect.unsafe.IORuntime

object AsyncComputation extends App {

  // Create an implicit IORuntime
  implicit val runtime: IORuntime = IORuntime.global

  private val asyncAction: IO[Int] = IO {
    Thread.sleep(1000) // blocking I/O
    10
  }

  private val combinedAction: IO[Int] = for {
    a <- asyncAction
    b <- IO(19)
  } yield a + b

  println(combinedAction.unsafeRunSync()) // Outputs: 29 after a delay

}
