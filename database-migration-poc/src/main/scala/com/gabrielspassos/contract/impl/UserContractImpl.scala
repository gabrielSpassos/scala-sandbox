package com.gabrielspassos.contract.impl

import com.gabrielspassos.contracts.v1
import com.gabrielspassos.contracts.v1.request.UserRequest
import com.gabrielspassos.contracts.v1.response.UserResponse
import com.gabrielspassos.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserContractImpl @Autowired(private val userService: UserService) extends v1.UserContract {

  override def createUser(userRequest: UserRequest): UserResponse = {
    userService.createUser(userRequest)
  }
  
}
