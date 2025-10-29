package com.gabrielspassos.contract.impl.v2

import com.gabrielspassos.contracts.v2
import com.gabrielspassos.service.v2.MigrationSimulatorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.lang.Boolean

@Component
class SimulatorContractImpl @Autowired(private val migrationSimulatorService: MigrationSimulatorService) extends v2.SimulatorContract {

  override def simulateMigration(isDryRunMode: Boolean): Boolean = {
    val simulations = migrationSimulatorService.simulateMigration(isDryRunMode)
    true
  }

}
