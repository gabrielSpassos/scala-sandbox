object Main extends App {
  println("Hello, World!")

  private val blackKnight = ChessPiece(Black, Knight)
  private val blackQueen = ChessPiece(Black, Queen)
  private val whiteQueen = ChessPiece(White, Queen)

  println(s"is $blackKnight, most important piece: ${isMostImportantChessPiece(blackKnight)}")
  println(s"is $blackQueen, most important piece: ${isMostImportantChessPiece(blackQueen)}")
  println(s"is $whiteQueen, most important piece: ${isMostImportantChessPiece(whiteQueen)}")

  private def isMostImportantChessPiece(piece: ChessPiece): Boolean = {
    piece match {
      case ChessPiece(_, Queen) => true
      case _ => false
    }
  }
}
