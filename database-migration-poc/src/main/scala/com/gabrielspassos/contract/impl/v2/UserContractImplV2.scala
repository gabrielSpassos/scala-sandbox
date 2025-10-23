package com.gabrielspassos.contract.impl.v2

import com.gabrielspassos.contracts.v2
import com.gabrielspassos.contracts.v2.request.UserRequest
import com.gabrielspassos.contracts.v2.response.UserResponse
import com.gabrielspassos.service.v2.UserServiceV2
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserContractImplV2 @Autowired(private val userService: UserServiceV2) extends v2.UserContract {

  override def createUser(userRequest: UserRequest): UserResponse = {
    val entity = userService.createUser(userRequest)

    val response = UserResponse()
    response.setUserId(entity.userId.toString)
    response
  }

}
