package resources

import cats.effect.{IO, IOApp}

object ResourceApp extends IOApp.Simple {

  override def run: IO[Unit] =
    for {
      res <- resources.ResourceService().fileWriter("foo.txt")
        .use(writer => IO(writer.write("Hello BOH!")))
    } yield ()

}