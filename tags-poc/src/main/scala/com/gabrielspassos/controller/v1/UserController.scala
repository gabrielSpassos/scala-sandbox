package com.gabrielspassos.controller.v1

import com.gabrielspassos.contract.impl.v1.UserContractImpl
import com.gabrielspassos.contracts.v1.request.UserRequest
import com.gabrielspassos.contracts.v1.response.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{PostMapping, RequestBody, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/v1/users"))
class UserController @Autowired()(private val userContract: UserContractImpl) {

  @PostMapping
  def createUser(@RequestBody userRequest: UserRequest): ResponseEntity[UserResponse] = {
    val userResponse = userContract.createUser(userRequest)
    ResponseEntity.status(HttpStatus.CREATED).body(userResponse)
  }

}
