package com.gabrielspassos.service

import com.gabrielspassos.entity.ItemEntity
import com.gabrielspassos.repository.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemService (repo: ItemRepository) {

  def upsert(item: ItemEntity): Boolean = {
    repo.upsert(item)
  }
  
  def findById(id: String): Option[ItemEntity] = {
    repo.findById(id)
  }
}
