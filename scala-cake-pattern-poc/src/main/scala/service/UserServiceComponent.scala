package com.gabrielspassos
package service

import domain.User

trait UserServiceComponent {

  def userService: UserService

  trait UserService {
    def findActiveUsers: List[User]
  }

}
