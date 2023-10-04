package com.gabrielspassos

import application.{LocalApp, LocalSecureApp}

@main
def main(): Unit = {
  println("LocalApp")
  val localApp = LocalApp
  println(localApp.userService.findActiveUsers)
  println(localApp.userRepository.findAll)
  println(localApp.accountService.findActiveAccounts)
  println(localApp.accountRepository.findAll)
  println(localApp.transactionService.updateAccountStatusToInactive("user@email.com", 123456L))

  println("LocalSecureApp")
  val localSecureApp = LocalSecureApp
  println(localSecureApp.userService.findActiveUsers)
  println(localSecureApp.accountService.findActiveAccounts)
  println(localSecureApp.transactionService.updateAccountStatusToInactive("user@email.com", 123456L))

}