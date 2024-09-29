package io

import cats.effect.{IO, IOApp}

import scala.concurrent.duration.DurationInt

object IOProgram extends IOApp.Simple {

  override def run: IO[Unit] =
    for {
      _ <- IO.println("What is your name?")
      name <- IO.readLine
      _ <- IO.sleep(1.second)
      _ <- IO.println(s"Hello, $name")
    } yield ()

}
