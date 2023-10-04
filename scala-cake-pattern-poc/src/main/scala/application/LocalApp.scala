package com.gabrielspassos
package application

import repository.{LocalAccountRepositoryComponent, LocalUserRepositoryComponent}
import service.{LocalAccountServiceComponent, LocalTransactionServiceComponent, LocalUserServiceComponent}

object LocalApp {
  val userServiceComponent = new LocalUserServiceComponent with LocalUserRepositoryComponent {

  }

  val accountServiceComponent = new LocalAccountServiceComponent with LocalAccountRepositoryComponent {

  }

  val transactionServiceComponent = new LocalTransactionServiceComponent
    with LocalUserServiceComponent with LocalUserRepositoryComponent
    with LocalAccountServiceComponent with LocalAccountRepositoryComponent {

  }

  val userService = userServiceComponent.userService
  val userRepository = userServiceComponent.userRepository

  val accountService = accountServiceComponent.accountService
  val accountRepository = accountServiceComponent.accountRepository

  val transactionService = transactionServiceComponent.transactionService
}
