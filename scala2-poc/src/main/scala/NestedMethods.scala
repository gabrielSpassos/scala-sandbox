import scala.annotation.tailrec

object NestedMethodsMain extends App{

  private def factorial(x: Int): Int = {

    @tailrec
    def fact(x: Int, accumulator: Int): Int = {
      if (x <= 1) accumulator
      else fact(x - 1, x * accumulator)
    }

    fact(x, 1)
  }

  println(factorial(2))
  println(factorial(5))
}
