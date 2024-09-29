package either

import cats.syntax.all.*
import future.ToFutureSuccessful

import scala.concurrent.Future

class DummyUserServiceImpl extends UserService {

  private def dummyUser: User = User(1L)

  override def isExistingUser(id: Int): Future[Either[Exception, User]] = dummyUser.asRight.asFuture
  // Future.successful(Right(dummyUser))

}
