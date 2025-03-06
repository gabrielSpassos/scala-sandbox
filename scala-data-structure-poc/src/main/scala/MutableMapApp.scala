import java.util
import java.util.UUID
import scala.collection.mutable
import scala.jdk.CollectionConverters.*

object MutableMapApp extends App {
  println("Mutable Map POC")

  private val mutableMap = mutable.Map[String, List[String]]()

  mutableMap += (UUID.randomUUID().toString -> List("a", "b", "c"))

  println(mutableMap)

  private val map1 = mutable.Map[String, util.List[String]]()
  map1 += (UUID.randomUUID().toString -> List("1", "2", "3").asJava)
  println(map1)

  private val immutableMap1 = Map(UUID.randomUUID().toString -> List("a", "b", "c"))
  immutableMap1.map(kv => map1 += (kv._1 -> kv._2.asJava))
  //immutableMap1.foreach { case (k, v) => map1 += (k -> v.asJava) }
  println(map1)

  private val immutableMap2 = Map(UUID.randomUUID().toString -> List("d", "e", "f"))
  immutableMap2.foreach { case (k, v) => map1 += (k -> v.asJava) }
  println(map1)

  private val immutableMap3 = map1.toMap
  private val mutableMapFromImmutableOne: mutable.Map[String, util.List[String]] =
    immutableMap1.map(kv => kv._1 -> kv._2.asJava).to(mutable.Map)
  println(mutableMapFromImmutableOne)
}