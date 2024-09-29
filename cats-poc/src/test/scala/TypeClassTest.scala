import scala.math.pow

class TypeClassTest extends munit.FunSuite {

  test("should show exponential values") {
    val list: List[Double] = List.apply(2, 3)

    val result = powNumbers(list)

    assertEquals(result, 8.0)
  }

  test("should join") {
    val list: List[String] = List.apply("hello", "get", "together!")

    val result = join(list)

    assertEquals(result, "hello get together! ")
  }

  test("should union sets") {
    val list: List[Set[Double]] = List.apply(Set.apply(1.0, 2.0), Set.apply(3.0, 4.0), Set.apply(5.0))

    val result = unionSets(list)

    assertEquals(result.size, 5)
  }


  def powNumbers(list: List[Double]): Double = list.foldRight(1.0)(pow(_, _).intValue)

  def join(list: List[String]): String = list.foldRight("")(_ ++ " " ++ _)

  def unionSets[A](list: List[Set[A]]): Set[A] = list.foldRight(Set.empty[A])(_ union _)
}
