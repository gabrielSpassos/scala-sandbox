package com.gabrielspassos.entity.v1

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.{Column, Table}

import java.time.OffsetDateTime
import scala.annotation.meta.field

@Table(name = "tags_v1")
case class TagsV1Entity(

   @(Id @field)
   id: String,

   @(Column @field)
   enabled: Boolean,

   @(Column @field)(value = "created_at")
   createdAt: OffsetDateTime = OffsetDateTime.now(),

   @(Column @field)(value = "updated_at")
   updatedAt: OffsetDateTime = OffsetDateTime.now(),
)


