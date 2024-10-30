case class Message(sender: String, recipient: String, body: String)

object CaseClassMain extends App {
  private val message1 = Message("Gabriel", "Josh", "test message")
  private val message2 = new Message("Gabriel", "Josh", "test message")
  private val message3 = message1.copy()
  private val message4 = message2.copy(recipient = "Mary", body = "real message")

  println(s"Are messages 1 and 2 equals? ${message1 == message2}")
  println(s"Are messages 1 and 3 equals? ${message1 == message3}")
  println(s"Are messages 2 and 4 equals? ${message2 == message4}")
}
