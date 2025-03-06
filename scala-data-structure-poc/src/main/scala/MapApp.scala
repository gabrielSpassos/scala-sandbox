import java.util.UUID

object MapApp extends App {

  println("Map POC")

  private val map = Map("a" -> 1, "b" -> 2, "c" -> 3)
  private val mapOfList = Map(UUID.randomUUID().toString -> List("a", "b", "c"))

  println(map)
  println(mapOfList)
}
