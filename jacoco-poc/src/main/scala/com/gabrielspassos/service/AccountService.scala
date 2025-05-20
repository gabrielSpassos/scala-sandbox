package com.gabrielspassos.service

import com.gabrielspassos.entity.Account
import com.gabrielspassos.repository.AccountRepository

class AccountService (private val accountRepository: AccountRepository) {

  def findAll(): List[Account] = {
    accountRepository.findAll()
  }

  def save(account: Account): Account = {
    accountRepository.save(account)
  }

}
