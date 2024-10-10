trait Feline

trait Animal

case class Cat() extends Animal with Feline

object CatApp extends App {
  private val cat = Cat
  println(cat)
}
