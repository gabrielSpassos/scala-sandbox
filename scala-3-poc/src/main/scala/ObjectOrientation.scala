trait SimpleTrait {
    def addOne(x: Int): Int = {
        x + 1
    }
}

class SimpleClass extends SimpleTrait

@main def ooMain: Unit =
  val simpleClass = new SimpleClass
  println(simpleClass.addOne(11))

  val simpleTrait = new SimpleTrait{}
  println(simpleTrait.addOne(17))
