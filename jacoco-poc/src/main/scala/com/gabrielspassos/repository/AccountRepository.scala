package com.gabrielspassos.repository

import com.gabrielspassos.entity.Account

import java.util.UUID
import scala.collection.mutable.ListBuffer

class AccountRepository {

  private val accounts = ListBuffer(Account(UUID.randomUUID()))

  def findAll(): List[Account] = {
    accounts.toList
  }

  def save(account: Account): Account = {
    accounts.addOne(account)
    account
  }

}
