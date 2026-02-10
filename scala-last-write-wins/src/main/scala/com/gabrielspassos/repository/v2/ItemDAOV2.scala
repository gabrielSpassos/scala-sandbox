package com.gabrielspassos.repository.v2

import com.gabrielspassos.entity.ItemEntity
import org.springframework.stereotype.Repository
import scala.jdk.OptionConverters.*

@Repository
class ItemDAOV2(repo: ItemRepositoryV2) {

  def save(itemEntity: ItemEntity): ItemEntity = {
    val result = repo.upsert(itemEntity)

//    if (!result) {
//      throw new IllegalStateException("fail to save item")
//    }

    findByExternalId(itemEntity.externalId).get
  }

  def findByExternalId(externalId: String): Option[ItemEntity] = {
    repo.findByExternalId(externalId).toScala
  }

}
