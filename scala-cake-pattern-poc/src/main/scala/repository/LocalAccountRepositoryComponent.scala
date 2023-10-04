package com.gabrielspassos
package repository
import domain.Account

import java.util.UUID

trait LocalAccountRepositoryComponent extends AccountRepositoryComponent {
  
  def accountRepository = new LocalAccountRepository()
  
  class LocalAccountRepository() extends AccountRepository {
    override def findAll: List[Account] = {
      val account = new Account(UUID.randomUUID.toString, 123456L, true)
      List(account)
    }
  }

}
