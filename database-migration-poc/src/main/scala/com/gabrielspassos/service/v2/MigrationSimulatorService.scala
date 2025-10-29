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
    val simulations = userNotMigrated.map { user =>
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

    val simulationsGroupedByCPF = simulations.groupBy(_.cpf)
    simulationsGroupedByCPF.foreach { case (cpf, simulationsWithSameCPF) =>
      if (simulationsWithSameCPF.length > 1) {
        simulationsWithSameCPF.foreach { simulation =>
          val index = simulations.indexOf(simulation)
          val updatedSimulation = simulation.copy(errors = simulation.errors :+ "Duplicate CPF in migration batch")
          simulations.updated(index, updatedSimulation)
        }
      }
    }

    if (!isDryRun) {
      println("Executing migration...")
      val usersToMigrate = simulations.filter(_.isSuccessful).map { simulation =>
        userNotMigrated.find(user => user.externalId1 == simulation.externalId1) match {
          case Some(user) =>
            Option(user.copy(cpf = simulation.cpf))
          case None =>
            None
        }
      }.filter(_.isDefined).map(_.get)

      userService.saveAll(usersToMigrate)
      println(s"Executed migration for count=${usersToMigrate.length} " +
        s"for users externalId1s=${usersToMigrate.map(_.externalId1).mkString(", ")}")
    }

    println("\nSIMULATOR RESULTS")
    println(
      s"""[SUMMARY]
      |successCount=${simulations.count(_.isSuccessful)}
      |failureCount=${simulations.count(!_.isSuccessful)}
      |""".stripMargin)
    simulations.foreach { simulation =>
      println(s"[DETAIL] externalId1=${simulation.externalId1} isSuccessful=${simulation.isSuccessful} errors=${simulation.errors.mkString(", ")}")
    }
    println()

    simulations
  }
}
