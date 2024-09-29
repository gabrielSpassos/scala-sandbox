package either

import scala.concurrent.Future

trait UserService {
  def isExistingUser(id: Int): Future[Either[Exception, User]]
}
