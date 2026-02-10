package com.gabrielspassos.repository

import com.gabrielspassos.entity.ItemEntity

trait ItemRepository {

  def upsert(item: ItemEntity): Boolean

  def findByExternalId(externalId: String): Option[ItemEntity]
  
}
