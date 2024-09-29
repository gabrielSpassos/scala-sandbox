package resources

import cats.effect.{IO, Resource}

import java.io.FileWriter

class ResourceService {
  def fileWriter(filename: String): Resource[IO, FileWriter] =
    Resource.make(
      IO(new FileWriter(filename))
    )(
      writer => IO(writer.close())
    )
}
