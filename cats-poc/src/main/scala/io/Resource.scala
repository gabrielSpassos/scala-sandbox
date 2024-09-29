package io

import cats.effect.unsafe.IORuntime
import cats.effect.{IO, Resource}

object ResourceApp extends App {

  // Create an implicit IORuntime
  implicit val runtime: IORuntime = IORuntime.global

  private val resource: Resource[IO, String] = Resource.make(
    IO(println("Acquiring resource")) *> IO.pure("Resource")
  )(res => IO(println(s"Releasing resource: $res")))

  val program: IO[Unit] = resource.use { res =>
    IO(println(s"Using resource: $res"))
  }

  program.unsafeRunSync()
}

