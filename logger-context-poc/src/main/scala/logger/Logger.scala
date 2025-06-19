package logger

class ContextLogger {

  def ofClass(clazz: Class[?]): Logger = {
    new Logger(clazz)
  }
}

private class Logger(private val clazz: Class[?]) {

  def info(message: String): Unit = {
    log(s"$message")
  }

  def error(message: String): Unit = {
    log(s"[ERROR]: $message")
  }

  def warn(message: String): Unit = {
    log(s"[WARN]: $message")
  }

  private def log(message: String): Unit = {
    val logSource = clazz.getSimpleName
    println(s"[source=$logSource] $message")
  }

}
