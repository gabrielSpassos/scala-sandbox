package com.gabrielspassos.entity.v3

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.{Column, Table}

import java.time.OffsetDateTime
import scala.annotation.meta.field

@Table(name = "tags_v3")
case class TagsV3Entity(

   @(Id @field)
   id: String,

   @(Column @field)
   value: String,

   @(Column @field)(value = "created_at")
   createdAt: OffsetDateTime = OffsetDateTime.now(),

   @(Column @field)(value = "updated_at")
   updatedAt: OffsetDateTime = OffsetDateTime.now(),
)


