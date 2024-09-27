package show

import cats.Show
import cats.implicits.toShow

import java.util.Date

object CustomShowInstance extends App {
  implicit val customShow: Show[Date] =
    new Show[Date] {
      override def show(t: Date): String =
        s"${t.getTime}ms since epoch"
    }
    
  private val showDate: String = new Date().show
  println(showDate)
}


