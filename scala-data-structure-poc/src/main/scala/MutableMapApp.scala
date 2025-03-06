import java.util.UUID
import scala.collection.mutable

object MutableMapApp extends App {
  println("Mutable Map")

  private val mutableMap = mutable.Map[String, List[String]]()

  mutableMap += (UUID.randomUUID().toString -> List("a", "b", "c"))

  println(mutableMap)
}