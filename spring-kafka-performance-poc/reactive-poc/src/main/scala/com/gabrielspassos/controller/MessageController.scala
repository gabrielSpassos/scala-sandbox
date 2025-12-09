package com.gabrielspassos.controller

import com.gabrielspassos.controller.response.MessageResponse
import com.gabrielspassos.service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{PostMapping, RequestMapping, RequestParam, RestController}
import reactor.core.publisher.Mono


@RestController
@RequestMapping(Array("/v1/messages"))
class MessageController @Autowired()(private val messageService: MessageService) {

  @PostMapping
  def postMessage(@RequestParam(value = "key") key: String,
                  @RequestParam(value = "message") message: String): Mono[MessageResponse] = {
    messageService.sendMessage("test-topic", key, message)
      .map(sendResult => MessageResponse(sendResult.getProducerRecord.key(), sendResult.getProducerRecord.value()))
  }

}

