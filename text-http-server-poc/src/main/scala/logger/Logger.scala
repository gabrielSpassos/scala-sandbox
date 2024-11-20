package logger

object Logger {

  def info(message: String, params: AnyRef*): Unit = {
    println(String.format(s"INFO: $message", params))
  }

  def warn(message: String, params: AnyRef*): Unit = {
    println(String.format(s"WARN: $message", params))
  }

  def error(message: String, error: Throwable, params: AnyRef*): Unit = {
    println(String.format(s"ERROR: $message. $error", params))
  }

  def error(message: String, params: AnyRef*): Unit = {
    println(String.format(s"ERROR: $message", params))
  }

}
