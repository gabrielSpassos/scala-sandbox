package com.gabrielspassos.service

import com.gabrielspassos.dao.CardDAO
import com.gabrielspassos.dto.CardDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CardService @Autowired()(private val cardDAO: CardDAO) {

  def findAll(): List[CardDTO] = {
    cardDAO.findAll()
      .map(entity => CardDTO.toDTO(entity))
  }
  
  def findByNumber(number: String): Option[CardDTO] = {
    cardDAO.findByNumber(number)
      .map(entity => CardDTO.toDTO(entity))
  }
  
  def findByInstitutionNameIn(institutionNames: List[String]): List[CardDTO] = {
    cardDAO.findByInstitutionNameIn(institutionNames)
      .map(entity => CardDTO.toDTO(entity))
  }

  def findByBrandIn(brands: List[String]): List[CardDTO] = {
    cardDAO.findByBrandIn(brands)
      .map(entity => CardDTO.toDTO(entity))
  }

}
