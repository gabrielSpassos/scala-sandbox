package com.gabrielspassos.dto

case class SimulationDTO(externalId1: String, cpf: String, errors: List[String] = List.empty) {
  
  def isSuccessful: Boolean = errors.isEmpty
  
}
