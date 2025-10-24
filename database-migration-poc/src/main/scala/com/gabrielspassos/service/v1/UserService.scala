package com.gabrielspassos.service.v1

import com.gabrielspassos.contracts.v1.request.UserRequest
import com.gabrielspassos.entity.UserEntity
import com.gabrielspassos.exception.BadRequestException
import com.gabrielspassos.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.jdk.OptionConverters.*

@Service
class UserService @Autowired()(private val userRepository: UserRepository) {

  def createUser(userRequest: UserRequest): UserEntity = {
    userRepository.findByExternalId1AndExternalId2(userRequest.getExternalId1, userRequest.getExternalId2).toScala match {
      case Some(existingUser) => throw new BadRequestException("User already exists")
      case None => ()
    }
    
    val entity = UserEntity(
      id = null,
      userId = null,
      cpf = null,
      externalId1 = userRequest.getExternalId1,
      externalId2 = userRequest.getExternalId2,
      status = UserEntity.activeStatus
    )
    userRepository.save(entity)
  }
  
  def findUserByExternalId1(externalId1: String): Option[UserEntity] = {
    userRepository.findByExternalId1(externalId1).toScala
  }

}
