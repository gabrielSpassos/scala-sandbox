package com.gabrielspassos
package service

import domain.Account

trait LocalTransactionServiceComponent extends TransactionServiceComponent {
  this: UserServiceComponent with AccountServiceComponent =>

  def transactionService = new LocalTransactionService

  class LocalTransactionService extends TransactionService {
    override def updateAccountStatusToInactive(userLogin: String, accountNumber: Long): Account = {
      val user = userService.findActiveUsers
        .find(u => u.login.equals(userLogin))
        .getOrElse(throw new IllegalArgumentException("Invalid user"))

      val account = accountService.findActiveAccounts
        .find(a => a.number.equals(accountNumber))
        .getOrElse(throw new IllegalArgumentException("Invalid account"))

      new Account(account.id, account.number, false)
    }
  }
}
