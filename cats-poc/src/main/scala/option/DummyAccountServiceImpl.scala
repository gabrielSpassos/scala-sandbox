package option

import cats.syntax.all.*
import future.ToFutureSuccessful

import scala.concurrent.Future

class DummyAccountServiceImpl extends AccountService {

  private def dummyAccount: Account = Account()

  override def getAccount: Future[Option[Account]] = dummyAccount.some.asFuture
  // Future.successful(Some(dummyAccount))
}
