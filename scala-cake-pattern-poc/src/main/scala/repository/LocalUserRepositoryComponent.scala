package com.gabrielspassos
package repository
import domain.User

import java.util.UUID

trait LocalUserRepositoryComponent extends UserRepositoryComponent {

  def userRepository = new LocalUserRepository()

  class LocalUserRepository() extends UserRepository {
    override def findAll: List[User] = {
      val user = User(UUID.randomUUID.toString, "user@email.com", true)
      List(user)
    }
  }

}
