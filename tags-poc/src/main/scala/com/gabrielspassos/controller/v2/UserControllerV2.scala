package com.gabrielspassos.controller.v2

import com.gabrielspassos.contract.impl.v2.UserContractImplV2
import com.gabrielspassos.contracts.v2.request.UserRequest
import com.gabrielspassos.contracts.v2.response.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{PostMapping, RequestBody, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/v2/users"))
class UserControllerV2 @Autowired()(private val userContract: UserContractImplV2) {

  @PostMapping
  def createUser(@RequestBody userRequest: UserRequest): ResponseEntity[UserResponse] = {
    val userResponse = userContract.createUser(userRequest)
    ResponseEntity.status(HttpStatus.CREATED).body(userResponse)
  }

}
