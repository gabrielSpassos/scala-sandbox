package monoid

class CollectionMonoidTest extends munit.FunSuite {

  test("should combine string collection") {
    val seq: Seq[String] = Seq("foo", "bar", "tar")
    
    val result = CollectionMonoid.combineAll(seq)
    
    assertEquals(result, "foobartar")
  }

  test("should combine int collection") {
    val seq: Seq[Int] = Seq(1, 20, 368)

    val result = CollectionMonoid.combineAll(seq)

    assertEquals(result, 389)
  }

}
