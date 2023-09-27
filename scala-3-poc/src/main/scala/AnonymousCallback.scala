def oncePerSecond(callback: () => Unit): Unit =
  while true do { callback(); Thread.sleep(1000) }

@main def TimerAnonymous: Unit =
  oncePerSecond(() =>
    println("time flies like an arrow..."))
