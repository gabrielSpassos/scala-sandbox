package calculator

import org.scalatest.funsuite.AnyFunSuite

class CalculatorTest extends AnyFunSuite {

  test("should return 8 as cube of 2") {
    val calculator = new Calculator()

    val result = calculator.cube(2)

    assert(8 == result)
  }

  test("should return 4 as square of 2") {
    val calculator = new Calculator()

    val result = calculator.square(2)

    assert(4 == result)
  }

}
