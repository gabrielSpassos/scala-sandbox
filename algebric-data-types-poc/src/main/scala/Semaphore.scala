trait Semaphore {
  val state: State
}

sealed trait State

case object Continue extends State
case object SlowDown extends State
case object Stop extends State
