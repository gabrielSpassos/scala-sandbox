class Tuples {

  def multiplyTuples(): Unit = {
    val pairs = Seq((2,5), (4, -7), (2, 8))
    for ((x, y) <- pairs) {
      println(x * y)
    }
  }

}

object TuplesMain extends App {
  private val tuples = new Tuples()
  tuples.multiplyTuples()
}
