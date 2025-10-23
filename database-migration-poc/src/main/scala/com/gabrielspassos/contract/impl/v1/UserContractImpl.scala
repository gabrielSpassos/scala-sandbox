package com.gabrielspassos.contract.impl.v1

import com.gabrielspassos.contracts.v1
import com.gabrielspassos.contracts.v1.request.UserRequest
import com.gabrielspassos.contracts.v1.response.UserResponse
import com.gabrielspassos.service.v1.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserContractImpl @Autowired(private val userService: UserService) extends v1.UserContract {

  override def createUser(userRequest: UserRequest): UserResponse = {
    val entity = userService.createUser(userRequest)

    val response = UserResponse()
    response.setExternalId1(entity.externalId1)
    response.setExternalId2(entity.externalId2)
    response.setStatus(entity.status)

    response
  }

}
