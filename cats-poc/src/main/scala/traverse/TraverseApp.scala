package traverse

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object BasicIOApp extends IOApp {
  def putStr(str: String): IO[Unit] = IO.delay(println(str))

  val launch = for {
    _ <- IO.delay(println("Hello Brazilians"))
    _ <- IO.delay(println("Happy to be on get together"))
  } yield ()

  override def run(args: List[String]): IO[ExitCode] = launch.as(ExitCode.Success)
}

object TraverseApp extends IOApp {
  def putStr(str: String): IO[Unit] = IO.delay(println(str))

  val tasks: List[Int] = (1 to 100).toList

  def taskExecutor(i: Int): String = s"Executing task $i"

  val runAllTasks: IO[List[Unit]] = tasks.traverse(i => putStr(taskExecutor(i)))

  override def run(args: List[String]): IO[ExitCode] = runAllTasks.as(ExitCode.Success)
}

object SequenceApp extends IOApp {
  def putStr(str: String): IO[Unit] = IO.delay(println(str))

  val tasks: List[IO[Int]] = (1 to 100).map(IO.pure).toList
  val sequenceAllTasks: IO[List[Int]] = tasks.sequence
  val printTaskSequence = sequenceAllTasks.map(_.mkString(", ")).flatMap(putStr)

  override def run(args: List[String]): IO[ExitCode] = printTaskSequence.as(ExitCode.Success)
}

object Utils {
  implicit class ShowThread[T](io: IO[T]) {
    def showThread: IO[T] = for {
      thunk <- io
      thread = Thread.currentThread.getName
      _ = println(s"[$thread] $thunk")
    } yield thunk
  }
}

import traverse.Utils.ShowThread

object ParallelApp extends IOApp {
  val tasks: List[IO[Int]] = (1 to 10).map(IO.pure).map(_.showThread).toList

  val incremented: IO[List[Int]] = tasks.parTraverse {
    ioi => for (i <- ioi) yield i + 1
  }

  val parallelOrNot = incremented.showThread

  override def run(args: List[String]): IO[ExitCode] = parallelOrNot.as(ExitCode.Success)
}

object NotParallelApp extends IOApp {
  val tasks: List[IO[Int]] = (1 to 10).map(IO.pure).map(_.showThread).toList

  val incremented: IO[List[Int]] = tasks.traverse { ioi =>
    for (i <- ioi) yield i + 1
  }

  val parallelOrNot: IO[List[Int]] = incremented.showThread

  override def run(args: List[String]): IO[ExitCode] =
    parallelOrNot.as(ExitCode.Success)

}