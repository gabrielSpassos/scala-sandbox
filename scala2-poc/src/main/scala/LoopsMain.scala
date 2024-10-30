object LoopsMain extends App {

  println("Scala Loops")

  private val numbers = Seq(1, 2, 3)

  for (x <- numbers) {
    val doubleX = x * 2
    println(s"number: $x, double x: $doubleX")
  }

  for {
    i <- 1 to 2
    j <- 'a' to 'b'
    k <- i to 10 by 4
  } {
    println(s"i: $i, j: $j, k: $k")
  }

  // guards
  for {
    i <- 1 to 5
    if i % 2 == 0
  } {
    println(s"Even number: $i")
  }

  // guards
  for {
    i <- 1 to 10
    if i > 3
    if i < 7
    if i % 2 == 0
  } {
    println(s"Even number with multiple guards: $i")
  }

  private val states = Map(
    "AK" -> "Alaska",
    "AL" -> "Alabama",
    "AR" -> "Arizona"
  )

  for ((code, name) <- states) println(s"abbrev: $code, name: $name")

  private val listResult =
    for (i <- 10 to 12)
    yield i * 2
  println(s"For Yield Result: $listResult")

  private val mapResult = (10 to 12).map(i => i * 2)
  println(s"Map Result: $mapResult")

  private val names = List("_olivia", "_walter", "_peter")
  private val capNames = for (name <- names) yield {
    val nameWithoutUnderscore = name.drop(1)
    val capName = nameWithoutUnderscore.capitalize
    capName
  }
  println(capNames)
}
