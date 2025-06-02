import java.util
import scala.jdk.CollectionConverters.*

object NoneMatchApp extends App {

  private val id1 = "id1"
  private val id2 = "id2"
  private val id3 = "id3"

  private val objectA = ObjectA(List(id1, id3).asJava)
  private val objectBList = List(ObjectB(id1), ObjectB(id2))
  private val emptyList = List[ObjectB]()

  private val allMatch1 = objectA.ids.asScala.forall(id => objectBList.exists(b => b.id.equals(id)))
  private val allMatch2 = allMatch(objectA, objectBList)
  private val allMatch3 = allMatch(objectA, emptyList)
  println(s"All match 1: $allMatch1")
  println(s"All match 2: $allMatch2")
  println(s"All match 3: $allMatch3")

  private val anyMatch1 = objectA.ids.asScala.exists(id => objectBList.exists(b => b.id.equals(id)))
  private val anyMatch2 = anyMatch(objectA, objectBList)
  private val anyMatch3 = anyMatch(objectA, emptyList)
  println(s"Any match 1: $anyMatch1")
  println(s"Any match 2: $anyMatch2")
  println(s"Any match 3: $anyMatch3")

  private val noneMatch1 = objectA.ids.asScala.forall(id => !(objectBList.exists(b => b.id.equals(id))))
  private val noneMatch2 = noneMatch(objectA, objectBList)
  private val noneMatch3 = noneMatch(objectA, emptyList)
  println(s"None match 1: $noneMatch1")
  println(s"None match 2: $noneMatch2")
  println(s"None match 3: $noneMatch3")

  private def allMatch(a: ObjectA, bList: List[ObjectB]): Boolean = {
    a.ids.asScala.forall(id => bList.exists(b => b.id.equals(id)))
  }

  private def anyMatch(a: ObjectA, bList: List[ObjectB]): Boolean = {
    a.ids.asScala.exists(id => bList.exists(b => b.id.equals(id)))
  }

  private def noneMatch(a: ObjectA, bList: List[ObjectB]): Boolean = {
    a.ids.asScala.forall(id => !bList.exists(b => b.id.equals(id)))
  }
}

case class ObjectA(ids: util.List[String])

case class ObjectB(id: String)
