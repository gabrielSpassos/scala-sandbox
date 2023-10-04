package com.gabrielspassos
package application

import org.scalatest.funsuite.AnyFunSuite

class LocalAppTest extends AnyFunSuite {

  test("findActiveUsers") {
    val activeUsers = LocalApp.userService.findActiveUsers
    assert(activeUsers.nonEmpty)
    assert(activeUsers.head.isActive)

  }

  test("findActiveAccounts") {
    val activeAccounts = LocalApp.accountService.findActiveAccounts
    assert(activeAccounts.nonEmpty)
    assert(activeAccounts.head.isActive)
  }

  test("updateAccountStatusToInactive") {
    val account = LocalApp.transactionService.updateAccountStatusToInactive("user@email.com", 123456L)
    assert(!account.isActive)
  }

  test("updateAccountStatusToInactive with invalid user") {
    val caught =
      intercept[IllegalArgumentException] {
        LocalApp.transactionService.updateAccountStatusToInactive("test@email.com", 123456L)
      }

    assert("Invalid user".equals(caught.getMessage))
  }

  test("updateAccountStatusToInactive with invalid account") {
    val caught =
      intercept[IllegalArgumentException] {
        LocalApp.transactionService.updateAccountStatusToInactive("user@email.com", 98765L)
      }

    assert("Invalid account".equals(caught.getMessage))
  }



}
