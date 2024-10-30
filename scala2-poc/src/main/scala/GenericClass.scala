class Stack[A] {
  private var elements: List[A] = Nil

  def push(value: A): Unit =
    elements = value :: elements

  def pop(): A = {
    val currentTop = peek
    elements = elements.tail
    currentTop
  }

  private def peek: A = elements.head

}

trait Fruit
class Apple extends Fruit
class Banana extends Fruit

object GenericClassMain extends App {
  println("Generic Class")
  private val doubleStack = new Stack[Double]

  doubleStack.push(2.5)
  doubleStack.push(8.9)
  println(doubleStack.pop())

  private val fruitStack = new Stack[Fruit]
  fruitStack.push(new Banana)
  fruitStack.push(new Apple)
  println(fruitStack.pop())
  println(fruitStack.pop())
}

