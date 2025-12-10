package com.gabrielspassos.controller

import com.gabrielspassos.controller.response.MessageResponse
import com.gabrielspassos.service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{PostMapping, RequestMapping, RequestParam, RestController}


@RestController
@RequestMapping(Array("/v1/messages"))
class MessageController @Autowired()(private val messageService: MessageService) {

  @PostMapping
  def postMessage(@RequestParam(value = "key") key: String,
                  @RequestParam(value = "message") message: String): MessageResponse = {
    val sendResult = messageService.sendMessage("test-topic", key, message)
    MessageResponse(sendResult.getProducerRecord.key(), sendResult.getProducerRecord.value())
  }

}

