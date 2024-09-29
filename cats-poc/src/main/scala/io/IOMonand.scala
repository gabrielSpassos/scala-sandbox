package io

import cats.effect.unsafe.IORuntime
import cats.effect.IO

@main def ioMonand(): Unit = {
  // Create an implicit IORuntime
  implicit val runtime: IORuntime = IORuntime.global

  val ioAction: IO[Int] = IO {
    // Some side-effecting computation
    println("Running a side effect!")
    42
  }

  // To execute the IO action:
  val result: Int = ioAction.unsafeRunSync()
  println(result) // Outputs: Running a side effect! 42
}
