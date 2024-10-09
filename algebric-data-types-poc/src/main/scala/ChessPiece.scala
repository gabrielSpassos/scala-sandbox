case class ChessPiece(color: Color, name: Name)

sealed trait Color

case object White extends Color

case object Black extends Color

sealed trait Name

case object Pawn extends Name

case object Rook extends Name

case object Knight extends Name

case object Bishop extends Name

case object Queen extends Name

case object King extends Name
