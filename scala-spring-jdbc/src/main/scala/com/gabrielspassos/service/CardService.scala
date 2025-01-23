package com.gabrielspassos.service

import com.gabrielspassos.dao.CardDAO
import com.gabrielspassos.dto.CardDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CardService @Autowired()(private val cardDAO: CardDAO) {

  def findAll(): List[CardDTO] = {
    cardDAO.findAll().map(entity => CardDTO.toDTO(entity))
  }

}
