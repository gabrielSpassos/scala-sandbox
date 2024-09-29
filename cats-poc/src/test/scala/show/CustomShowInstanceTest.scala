package show

import cats.Show
import cats.implicits.*

import java.time.ZonedDateTime
import java.util.Date

class CustomShowInstanceTest extends munit.FunSuite {

  implicit val customShow: Show[Date] = (date: Date) => s"${date.getTime}ms since epoch"

  test("should return date show") {
    val date = Date.from(ZonedDateTime.parse("2024-09-26T10:15:30-03:00[America/Sao_Paulo]").toInstant)

    assert(date != null)

    assertEquals("1727356530000ms since epoch", date.show)
  }

}
