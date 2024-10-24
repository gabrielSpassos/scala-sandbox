import scala.concurrent.ExecutionContext

object MultipleParametersListMain extends App {

  private val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

  private def foldLeft1[A, B](aList: List[A], firstB: B, operation: (B, A) => B) = aList.foldLeft(firstB)(operation)
  private def firstWay = foldLeft1[Int, Int](numbers, 0, _ + _)
  private def secondWay = foldLeft1(numbers, 0, (a: Int, b: Int) => a + b)
  println(firstWay)
  println(secondWay)

  private def foldRight2[A, B](aList: List[A], firstB: B)(operation: (A, B) => B) = aList.foldRight(firstB)(op = operation)
  private def foldRight2Implementation = foldRight2(numbers, 0)(_ + _)
  println(foldRight2Implementation)

  private def execute(arg: Int)(implicit ec: scala.concurrent.ExecutionContext): Unit = {
    ec.execute(() => { println(s"Execution param $arg") })
  }

  private val ec = ExecutionContext.global
  execute(12)(ec)
}