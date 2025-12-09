package com.gabrielspassos.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

import java.util.concurrent.CompletableFuture

@Service
class MessageService @Autowired()(private val kafkaTemplate: KafkaTemplate[String, String]) {

  def sendMessage(topic: String, key: String, value: String): Mono[SendResult[String, String]] = {
    val future: CompletableFuture[SendResult[String, String]] =
      kafkaTemplate.send(topic, key, value)

    Mono.fromFuture(future)
  }

}

