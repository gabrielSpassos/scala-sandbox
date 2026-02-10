package com.gabrielspassos.repository.v2

import com.gabrielspassos.entity.ItemEntity

trait ItemUpsertRepository {

  def upsert(item: ItemEntity): Boolean
  
}
