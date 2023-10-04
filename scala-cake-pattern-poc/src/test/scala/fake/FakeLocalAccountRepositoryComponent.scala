package com.gabrielspassos
package fake

import domain.Account
import repository.AccountRepositoryComponent

import java.util.UUID

trait FakeLocalAccountRepositoryComponent extends AccountRepositoryComponent {

  def accountRepository = new FakeLocalAccountRepository()

  class FakeLocalAccountRepository() extends AccountRepository {
    override def findAll: List[Account] = {
      val account = new Account(UUID.randomUUID.toString, 123456L, false)
      List(account)
    }
  }
}
