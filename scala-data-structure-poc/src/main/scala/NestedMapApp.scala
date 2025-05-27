import java.util.UUID

object NestedMapApp extends App {
  println("Nested Map POC")

  private val id1 = UUID.randomUUID()
  private val accountEntity1 = AccountEntity(id = id1, name = "account1", state = "Active")
  private val accountResponse1 = AccountResponse(id = id1, number = "123456")

  private val id2 = UUID.randomUUID()
  private val accountEntity2 = AccountEntity(id = id2, name = "account2", state = "Disabled")
  private val accountResponse2 = AccountResponse(id = id2, number = null)

  private val id3 = UUID.randomUUID()
  private val accountEntity3 = AccountEntity(id = id3, name = "account3", state = "Active")

  private val id4 = UUID.randomUUID()
  private val accountResponse4 = AccountResponse(id = id4, number = null)

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
}

case class Account(number: String, state: String)

case class AccountEntity(id: UUID, name: String, state: String)

case class AccountResponse(id: UUID, number: String)
