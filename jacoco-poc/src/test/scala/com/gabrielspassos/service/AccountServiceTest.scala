package com.gabrielspassos.service

import com.gabrielspassos.entity.Account
import com.gabrielspassos.repository.AccountRepository
import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertNotNull}
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.when
import org.mockito.junit.jupiter.MockitoExtension

import java.util.UUID
import scala.compiletime.uninitialized

@ExtendWith(Array(classOf[MockitoExtension]))
class AccountServiceTest {

  @Mock
  private var accountRepository: AccountRepository = uninitialized

  @Test
  def shouldFindAllAccounts(): Unit = {
    val accountService = AccountService(accountRepository)
    val account = Account(UUID.randomUUID())

    when(accountRepository.findAll()).thenReturn(List(account))

    val accounts: List[Account] = accountService.findAll()

    assertNotNull(accounts)
    assertFalse(accounts.isEmpty)
    assertEquals(1, accounts.size)
    assertEquals(account, accounts.head)
  }

  @Test
  def shouldSaveAccount(): Unit = {
    val accountService = AccountService(accountRepository)
    val account = Account(UUID.randomUUID())

    when(accountRepository.save(account)).thenReturn(account)

    val savedAccount: Account = accountService.save(account)

    assertNotNull(savedAccount)
    assertEquals(account, savedAccount)
  }

}
