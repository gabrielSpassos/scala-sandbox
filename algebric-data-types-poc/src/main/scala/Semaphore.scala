trait Semaphore {
  val state: State
}

case class SemaphoreImpl(state: State) extends Semaphore

sealed trait State

case object Continue extends State
case object SlowDown extends State
case object Stop extends State

object SemaphoreApp extends App {

  println(runSemaphore(Continue))
  println(runSemaphore(SlowDown))
  println(runSemaphore(Stop))

  private def runSemaphore(state: State): Semaphore = {
    state match {
      case Continue => SemaphoreImpl(Continue)
      case SlowDown => SemaphoreImpl(SlowDown)
      case Stop => SemaphoreImpl(Stop)
    }
  }
}