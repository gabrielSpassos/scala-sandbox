package com.gabrielspassos.service

import com.gabrielspassos.entity.ItemEntity
import com.gabrielspassos.repository.v1.ItemRepositoryV1
import org.springframework.stereotype.Service

@Service
class ItemServiceV1(repo: ItemRepositoryV1) {

  def upsert(item: ItemEntity): Boolean = {
    repo.upsert(item)
  }
  
  def findByExternalId(externalId: String): Option[ItemEntity] = {
    repo.findByExternalId(externalId)
  }
}
