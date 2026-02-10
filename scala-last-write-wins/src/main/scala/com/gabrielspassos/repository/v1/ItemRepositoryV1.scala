package com.gabrielspassos.repository.v1

import com.gabrielspassos.entity.ItemEntity

trait ItemRepositoryV1 {

  def upsert(item: ItemEntity): Boolean

  def findByExternalId(externalId: String): Option[ItemEntity]
  
}
