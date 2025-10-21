package com.gabrielspassos.service

import com.gabrielspassos.contracts.v1.request.UserRequest
import com.gabrielspassos.contracts.v1.response.UserResponse
import org.springframework.stereotype.Service

@Service
class UserService {

  def createUser(userRequest: UserRequest): UserResponse = {
    UserResponse()
  }
  
}
