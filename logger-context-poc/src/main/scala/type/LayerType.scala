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

  def fromString(className: String): LayerType = {
    if (className == null || className.isBlank) {
      return UnknowLayer()
    }
    
    if (className.toLowerCase.endsWith("client")) ClientLayer()
    else if (className.toLowerCase.endsWith("service")) ServiceLayer()
    else if (className.toLowerCase.endsWith("controller")) ControllerLayer()
    else UnknowLayer()
  }
  
}