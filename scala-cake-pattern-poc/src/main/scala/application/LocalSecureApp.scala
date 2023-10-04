package com.gabrielspassos
package application

import repository.{LocalAccountRepositoryComponent, LocalUserRepositoryComponent}
import service.{LocalAccountServiceComponent, LocalTransactionServiceComponent, LocalUserServiceComponent}

object LocalSecureApp {

  val userService = new LocalUserServiceComponent with LocalUserRepositoryComponent {}.userService
  val accountService = new LocalAccountServiceComponent with LocalAccountRepositoryComponent {}.accountService
  val transactionService = new LocalTransactionServiceComponent
    with LocalUserServiceComponent with LocalUserRepositoryComponent
    with LocalAccountServiceComponent with LocalAccountRepositoryComponent {}.transactionService

}
