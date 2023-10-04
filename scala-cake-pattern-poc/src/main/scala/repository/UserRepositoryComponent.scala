package com.gabrielspassos
package repository

import domain.User

trait UserRepositoryComponent {

  def userRepository: UserRepository

  trait UserRepository {
    def findAll: List[User]
  }
}
