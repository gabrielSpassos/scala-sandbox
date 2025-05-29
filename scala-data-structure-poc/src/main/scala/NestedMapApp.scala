import java.util.UUID

object NestedMapApp extends App {
  println("Nested Map POC")

  private val id1 = UUID.randomUUID()
  private val accountEntity1 = AccountEntity(id = id1, code = 1, state = "Active")
  private val accountResponse1 = AccountResponse(id = id1, code = 1, number = "123456")

  private val id2 = UUID.randomUUID()
  private val accountEntity2 = AccountEntity(id = id2, code = 2, state = "Disabled")
  private val accountResponse2 = AccountResponse(id = id2, code = 2, number = null)

  private val id3 = UUID.randomUUID()
  private val accountEntity3 = AccountEntity(id = id3, code = 3, state = "Active")

  private val id4 = UUID.randomUUID()
  private val accountResponse4 = AccountResponse(id = id4, code = 4, number = null)

  private val entities = List(accountEntity1, accountEntity2, accountEntity3)
  private val responses = List(accountResponse1, accountResponse2, accountResponse4)

  private val list: List[(AccountEntity, Option[String])] = entities.map { entity =>
    val number = responses.find { response => response.id.equals(entity.id) }.flatMap(response => Option(response.number))
    (entity, number)
  }
  println(list)

  private val map: Map[String, Account] = list.map { (accountEntity, bankAccountNumber) =>
    Map(accountEntity.id.toString -> Account(number = bankAccountNumber.orNull, state = accountEntity.state))
  }.foldLeft(Map.empty[String, Account])((acc, map) => acc.concat(map))
  println(map)

  private val eitherListByCode: List[Either[String, Option[AccountEntity]]] = responses
    .map { response => getAccountByCode(response.code) }
  println(eitherListByCode)

  private val convertedEitherListByCode = convertEitherListRight(eitherListByCode)
  println(convertedEitherListByCode)

  private val eitherListById: List[Either[String, Option[AccountEntity]]] = responses
    .map { response => getAccountById(response.id) }
  println(eitherListById)

  private val convertedEitherListById = convertEitherListRight(eitherListById)
  println(convertedEitherListById)

  private def getAccountByCode(code: Int): Either[String, Option[AccountEntity]] = {
    val isEven = code % 2 == 0

    if (isEven) {
      Left(s"Failure to get account by code=$code")
    } else {
      Right(entities.find(account => account.code == code))
    }
  }

  private def getAccountById(id: UUID): Either[String, Option[AccountEntity]] = {
    Right(entities.find(account => account.id.equals(id)))
  }

  private def convertEitherListRight[Error, Result](eitherList: List[Either[Error, Result]]): Either[Error, List[Result]] = {
    eitherList.foldRight(Right(List.empty[Result]): Either[Error, List[Result]]) { (either, acc) =>
      for {
        list <- acc
        item <- either
      } yield item :: list
    }
  }
}

case class Account(number: String, state: String)

case class AccountEntity(id: UUID, code: Int, state: String)

case class AccountResponse(id: UUID, code: Int, number: String)
