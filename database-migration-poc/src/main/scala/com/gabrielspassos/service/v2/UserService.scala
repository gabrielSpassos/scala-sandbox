package com.gabrielspassos.service.v2

import com.gabrielspassos.contracts.v2.request.UserRequest
import com.gabrielspassos.entity.UserEntity
import com.gabrielspassos.exception.BadRequestException
import com.gabrielspassos.repository.UserRepository
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

import java.security.MessageDigest
import java.util.UUID
import scala.jdk.OptionConverters.*

@Service
class UserService @Autowired()(private val userRepository: UserRepository) {
  
  def createUser(userRequest: UserRequest): UserEntity = {
    val encryptedCpf = encryptString(userRequest.getCpf)
    
    userRepository.findByCpf(encryptedCpf).toScala match {
      case Some(existingUser) => throw new BadRequestException("User already exists")
      case None => ()
    }
    
    val userId = UUID.randomUUID()
    val entity = UserEntity(
      id = null,
      userId = userId,
      cpf = encryptedCpf,
      externalId1 = null,
      externalId2 = null,
      status = UserEntity.activeStatus
    )
    userRepository.save(entity)
  }
  
  def findUserByUserId(userId: String): Option[UserEntity] = {
    Option(userId)
      .map(id => UUID.fromString(id))
      .flatMap(id => userRepository.findByUserId(id).toScala)
  }

  private def encryptString(value: String): String = {
    val digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes("UTF-8"))
    digest.map("%02x".format(_)).mkString
  }

}
