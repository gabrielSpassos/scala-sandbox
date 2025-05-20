package com.gabrielspassos.integrationtests.repository

import com.gabrielspassos.entity.Account
import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertNotNull, assertTrue}
import org.junit.jupiter.api.Test
import com.gabrielspassos.repository.AccountRepository

import java.util.UUID


class AccountRepositoryIntegrationTest {

  @Test
  def shouldReturnEmptyAccounts(): Unit = {
    val accountRepository = AccountRepository()

    val accounts: List[Account] = accountRepository.findAll()

    assertNotNull(accounts)
    assertFalse(accounts.isEmpty)
    assertEquals(1, accounts.size)
  }

  @Test
  def shouldSaveAccount(): Unit = {
    val accountRepository = AccountRepository()
    val account = Account(UUID.randomUUID())

    val savedAccount: Account = accountRepository.save(account)

    assertNotNull(savedAccount)
    assertEquals(account, savedAccount)
  }


}
