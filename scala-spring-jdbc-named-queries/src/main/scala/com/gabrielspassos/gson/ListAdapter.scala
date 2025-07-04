package com.gabrielspassos.gson

import com.google.gson.*
import com.google.gson.reflect.TypeToken

import java.lang.reflect.{ParameterizedType, Type}
import scala.jdk.CollectionConverters.*
import scala.reflect.ClassTag

class ListAdapter[T](implicit ct: ClassTag[T]) extends JsonSerializer[List[T]] with JsonDeserializer[List[T]] {
  override def serialize(src: List[T], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    context.serialize(src.asJava)
  }

  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List[T] = {
    try {
      val clazz = getNestedClass(typeOfT)
      val listType = TypeToken.getParameterized(classOf[java.util.List[?]], clazz).getType
      val array = context.deserialize[java.util.List[T]](json, listType)
      array.asScala.toList
    } catch {
      case e: Exception => throw new JsonParseException(e)
    }
  }

  private def getNestedClass(typeOfT: Type): Class[?] = {
    typeOfT match {
      case pt: ParameterizedType => pt.getActualTypeArguments.head match {
        case cls: Class[_] => cls
        case _ => throw new IllegalArgumentException("Type argument is not a class")
      }
      case _ => ct.runtimeClass
    }
  }
}
