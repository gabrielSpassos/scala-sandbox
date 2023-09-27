@main def hello: Unit =
  println("Hello world!")
  println(msg)
  println(msg2)

def msg = "I was compiled by Scala 3. :)"
def msg2 = "I am a new line\nSecond new line."
