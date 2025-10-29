package com.gabrielspassos.controller.v2

import com.gabrielspassos.contract.impl.v2.SimulatorContractImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Array("/v2/simulators"))
class SimulatorControllerV2 @Autowired()(private val simulatorContractImpl: SimulatorContractImpl) {

  @PostMapping
  def createReport(@RequestParam(name = "isDryRunMode", required = false) isDryRunMode: Boolean = true): ResponseEntity[Void] = {
    simulatorContractImpl.simulateMigration(isDryRunMode)
    ResponseEntity.status(HttpStatus.NO_CONTENT).build()
  }

}
