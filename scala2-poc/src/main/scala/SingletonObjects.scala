

object Logger {
  def info(message: String): Unit = {
    println(s"INFO: $message")
  }

  def warn(message: String): Unit = {
    println(s"WARN: $message")
  }

  def error(message: String, error: Throwable): Unit = println(s"ERROR: $message. $error")

}

import Logger.{error, info}

object SingletonObjectsMain extends App {
  try {
    info("Scala 20 poc running")
    throw new RuntimeException("test purpose error")
  } catch {
    case e: Throwable => error("Error caused on purpose", e)
  }

}