package com.gabrielspassos.service

import com.gabrielspassos.entity.ItemEntity
import com.gabrielspassos.repository.v2.ItemDAOV2
import org.springframework.stereotype.Service

@Service
class ItemServiceV2(dao: ItemDAOV2) {

  def save(item: ItemEntity): ItemEntity = {
    dao.save(item)
  }
  
  def findByExternalId(externalId: String): Option[ItemEntity] = {
    dao.findByExternalId(externalId)
  }
}
