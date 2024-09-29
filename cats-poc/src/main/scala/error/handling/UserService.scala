package error.handling

import cats.effect.{IO, IOApp}

class UserService {

  def handleError(): IO[Unit] = {
    val failedIO: IO[Nothing] = IO.raiseError(
      new IllegalStateException("NOPE")
    )

    failedIO.handleErrorWith(
      error => IO.println(s"Error: $error")
    )
  }
  
}

object Main extends IOApp.Simple {
  val run: IO[Unit] = {
    val userService = new UserService()
    userService.handleError()
  }
}

