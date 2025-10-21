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
    val entity = userService.createUser(userRequest)

    val response = UserResponse()
    response.setId(entity.id.toString)
    response.setUserId(Option(entity.userId).map(_.toString).orNull)
    response.setCpf(entity.cpf)
    response.setExternalId1(entity.externalId1)
    response.setExternalId2(entity.externalId2)
    response.setStatus(entity.status)
    response.setCreatedAt(entity.createdAt.toString)
    response.setUpdatedAt(entity.updatedAt.toString)

    response
  }

}
