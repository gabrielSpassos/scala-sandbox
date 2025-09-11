package com.gabrielspassos.gson

import com.google.gson.*

import java.lang.reflect.Type
import scala.collection.immutable.Seq
import scala.jdk.CollectionConverters.*

class SeqAdapter[T](implicit m: Manifest[T]) extends JsonSerializer[Seq[T]] with JsonDeserializer[Seq[T]] {
  override def serialize(src: Seq[T], t: Type, ctx: JsonSerializationContext): JsonElement = {
    val arr = new JsonArray()
    src.foreach(e => arr.add(ctx.serialize(e)))
    arr
  }

  override def deserialize(json: JsonElement, t: Type, ctx: JsonDeserializationContext): Seq[T] = {
    json.getAsJsonArray.iterator().asScala.map(e => ctx.deserialize[T](e, m.runtimeClass)).toSeq
  }
}


