package com.gabrielspassos.repository

import com.gabrielspassos.entity.ItemEntity

trait ItemRepository {

  def upsert(item: ItemEntity): Boolean

  def findById(id: String): Option[ItemEntity]
  
}
