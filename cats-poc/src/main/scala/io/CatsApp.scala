package io

import cats.effect.{IO, IOApp}

import scala.concurrent.duration.*

object CatsApp extends IOApp.Simple {

  override def run: IO[Unit] =
    for {
      _ <- IO.sleep(2.seconds)
      _ <- IO.println("Hello World!")
    } yield ()

}
