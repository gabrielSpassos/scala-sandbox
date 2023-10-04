package com.gabrielspassos
package service

import domain.User
import repository.UserRepositoryComponent

trait LocalUserServiceComponent extends UserServiceComponent {
  this: UserRepositoryComponent =>

  def userService = new LocalUserService

  class LocalUserService extends UserService {
    override def findActiveUsers: List[User] = {
      userRepository.findAll.filter(user => user.isActive)
    }
  }

}
