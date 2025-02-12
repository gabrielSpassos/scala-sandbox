package com.gabrielspassos.gson

import com.google.gson.*
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type
import scala.jdk.CollectionConverters.*
import scala.reflect.ClassTag

class ListAdapter[T](implicit ct: ClassTag[T]) extends JsonSerializer[List[T]] with JsonDeserializer[List[T]] {
  override def serialize(src: List[T], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    context.serialize(src.asJava)
  }

  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List[T] = {
    try {
      val listType = TypeToken.getParameterized(classOf[java.util.List[?]], ct.runtimeClass).getType
      val array = context.deserialize[java.util.List[T]](json, listType)
      array.asScala.toList
    } catch {
      case e: Exception => throw new JsonParseException(e)
    }
  }
}
