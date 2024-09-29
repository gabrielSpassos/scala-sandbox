package option

import scala.concurrent.Future

trait AccountService {
  def getAccount: Future[Option[Account]]
}
