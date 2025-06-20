package `type`

sealed trait LayerType {
  def typeName: String
}

case class ClientLayer() extends LayerType {
  override def typeName: String = "CLIENT"
}

case class ServiceLayer() extends LayerType {
  override def typeName: String = "SERVICE"
}

case class ControllerLayer() extends LayerType {
  override def typeName: String = "CONTROLLER"
}

case class UnknowLayer() extends LayerType {
  override def typeName: String = "UNKNOWN"
}

object LayerType {

  def fromClass(clazz: Class[?]): String = {
    if (null == clazz) {
      return UnknowLayer().typeName
    }
    
    val className = clazz.getSimpleName
    if (null == className || className.isBlank) {
      return UnknowLayer().typeName
    }
    
    if (className.toLowerCase.endsWith("client")) ClientLayer().typeName
    else if (className.toLowerCase.endsWith("service")) ServiceLayer().typeName
    else if (className.toLowerCase.endsWith("controller")) ControllerLayer().typeName
    else UnknowLayer().typeName
  }
  
}