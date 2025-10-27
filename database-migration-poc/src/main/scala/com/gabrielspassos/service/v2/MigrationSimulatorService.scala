package com.gabrielspassos.service.v2

import com.gabrielspassos.client.CustomerManagerClient
import com.gabrielspassos.dto.SimulationDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.mutable.ListBuffer

@Service
class MigrationSimulatorService @Autowired()(private val userService: UserServiceV2,
                                             private val customerManagerClient: CustomerManagerClient) {

  def simulateMigration(isDryRun: Boolean = true): List[SimulationDTO] = {
    val userNotMigrated = userService.findUsersWithoutCpf()
    userNotMigrated.map { user =>
      val errors = ListBuffer[String]()
      var encryptedCpf: String = null
      
      if (user.externalId1 == null || user.externalId1.isBlank) {
        errors += "User has invalid externalId1"
      } else {
        val cpfOption = customerManagerClient.getCustomerCpfByExternalId1(user.externalId1)

        if (cpfOption.isEmpty) {
          errors += s"CPF not found for externalId1: ${user.externalId1}"
        } else {
          encryptedCpf = userService.encryptCpf(cpfOption.get)
        }
      }
      
      SimulationDTO(user.externalId1, encryptedCpf, errors.toList)
    }
  }
}
