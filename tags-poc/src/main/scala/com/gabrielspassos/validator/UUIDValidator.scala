package com.gabrielspassos.validator

import java.util.UUID

object UUIDValidator {
  
  def isValidUUID(uuidAsString: String): (Boolean, Option[UUID]) = {
    try {
      val uuid = UUID.fromString(uuidAsString)
      (true, Option(uuid))
    } catch {
      case e: Exception =>
        (false, None)
    }
  }

}
