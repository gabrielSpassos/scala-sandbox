package com.gabrielspassos
package service

import domain.Account
import repository.AccountRepositoryComponent

trait LocalAccountServiceComponent extends AccountServiceComponent {
  this: AccountRepositoryComponent => 
  
  def accountService = new LocalAccountService
  
  class LocalAccountService extends AccountService {
    override def findActiveAccounts: List[Account] = {
      accountRepository.findAll.filter(account => account.isActive)
    }
  }

}
