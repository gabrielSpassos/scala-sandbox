package com.gabrielspassos.service

import com.gabrielspassos.contracts.v1.request.UserRequest
import com.gabrielspassos.entity.UserEntity
import com.gabrielspassos.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired()(private val userRepository: UserRepository){

  def createUser(userRequest: UserRequest): UserEntity = {
    val entity = UserEntity(
      id = null,
      userId = null,
      cpf = null,
      externalId1 = userRequest.getExternalId1,
      externalId2 = userRequest.getExternalId2,
      status = "ACTIVE"
    )
    userRepository.save(entity)
  }

}
