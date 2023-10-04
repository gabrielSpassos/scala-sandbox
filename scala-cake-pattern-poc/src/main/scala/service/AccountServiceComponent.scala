package com.gabrielspassos
package service

import domain.Account

trait AccountServiceComponent {

  def accountService: AccountService
  
  trait AccountService {
    def findActiveAccounts: List[Account]
  }
}
