package monoid

import cats.Monoid

class CollectionMonoidTest extends munit.FunSuite {

  test("should combine string collection") {
    val seq: Seq[String] = Seq("foo", "bar", "tar")
    val monoid = Monoid[String]
    
    val result = CollectionMonoid.combineAll(seq, monoid)
    
    assertEquals(result, "foobartar")
  }

  test("should combine int collection") {
    val seq: Seq[Int] = Seq(1, 20, 368)
    val monoid = Monoid[Int]

    val result = CollectionMonoid.combineAll(seq, monoid)

    assertEquals(result, 389)
  }

}
