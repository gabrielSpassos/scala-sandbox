package com.gabrielspassos
package service

import domain.Account

trait TransactionServiceComponent {
  
  def transactionService: TransactionService

  trait TransactionService {
    def updateAccountStatusToInactive(userLogin: String, accountNumber: Long): Account
  }

}
