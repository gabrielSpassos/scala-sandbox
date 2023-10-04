package com.gabrielspassos
package repository

import domain.Account

trait AccountRepositoryComponent {

  def accountRepository: AccountRepository
  
  trait AccountRepository {
    def findAll: List[Account]
  }
}
