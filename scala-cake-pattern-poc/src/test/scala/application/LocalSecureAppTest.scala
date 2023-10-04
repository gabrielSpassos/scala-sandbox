package com.gabrielspassos
package application

import fake.FakeLocalAccountRepositoryComponent
import service.LocalAccountServiceComponent

import org.scalatest.funsuite.AnyFunSuiteLike


trait TestLocalSecureApp extends LocalAccountServiceComponent with FakeLocalAccountRepositoryComponent {

}

class LocalSecureAppTest extends AnyFunSuiteLike with TestLocalSecureApp{

  test("findActiveAccounts should return empty") {
    val activeAccounts = accountService.findActiveAccounts
    assert(activeAccounts.isEmpty)
  }

}
