import java.util
import scala.jdk.CollectionConverters.*

object NoneMatchApp extends App {

  private val id1 = "id1"
  private val id2 = "id2"
  private val id3 = "id3"

  private val objectA = ObjectA(List(id1, id3).asJava)
  private val objectBList = List(ObjectB(id1), ObjectB(id2))

  private val allMatch = objectA.ids.asScala.forall(id => objectBList.exists(b => b.id.equals(id)))
  println(s"All match $allMatch")

  private val anyMatch = objectA.ids.asScala.exists(id => objectBList.exists(b => b.id.equals(id)))
  println(s"Any match $anyMatch")

  private val noneMatch = objectA.ids.asScala.forall(id => !(objectBList.exists(b => b.id.equals(id))))
  println(s"None match $noneMatch")
}

case class ObjectA(ids: util.List[String])

case class ObjectB(id: String)
