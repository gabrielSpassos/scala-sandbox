package semigroups

class CollectionSemigroupTest extends munit.FunSuite {

  test("should combine string collection") {
    val seq: Seq[String] = Seq("foo", "bar", "tar")
    
    val result = CollectionSemigroup.combineStrings(seq)
    
    assertEquals(result, "foobartar")
  }

}
