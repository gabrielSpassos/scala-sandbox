package com.gabrielspassos.entity.v2

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.{Column, Table}

import java.time.OffsetDateTime
import scala.annotation.meta.field

@Table(name = "tags_v2")
case class TagsV2Entity(

   @(Id @field)
   id: String,

   @(Column @field)(value = "enabled_at")
   enabledAt: OffsetDateTime = null,

   @(Column @field)(value = "disabled_at")
   disabledAt: OffsetDateTime = null,

   @(Column @field)(value = "created_at")
   createdAt: OffsetDateTime = OffsetDateTime.now(),

   @(Column @field)(value = "updated_at")
   updatedAt: OffsetDateTime = OffsetDateTime.now(),
)


