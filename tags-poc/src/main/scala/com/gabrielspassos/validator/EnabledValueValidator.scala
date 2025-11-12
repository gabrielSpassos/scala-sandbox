package com.gabrielspassos.validator

sealed trait EnabledValue {
  def valueAsString: String
}

case object On extends EnabledValue {
  override val valueAsString: String = "ON"
}

case object Off extends EnabledValue {
  override val valueAsString: String = "OFF"
}

case object Invalid extends EnabledValue {
  override val valueAsString: String = "INVALID"
}

object EnabledValueValidator {

  def isValid(enabledValueAsString: String): (Boolean, EnabledValue) = {
    enabledValueAsString.toUpperCase match {
      case "ON"  => (true, On)
      case "OFF" => (true, Off)
      case _     => (false, Invalid)
    }
  }

}
