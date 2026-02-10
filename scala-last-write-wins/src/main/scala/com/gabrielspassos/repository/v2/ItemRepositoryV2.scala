package com.gabrielspassos.repository.v2

import com.gabrielspassos.entity.ItemEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import java.util.{Optional, UUID}

@Repository
trait ItemRepositoryV2 extends CrudRepository[ItemEntity, UUID] 
    with ItemUpsertRepository {

  def findByExternalId(externalId: String): Optional[ItemEntity]
  
}
