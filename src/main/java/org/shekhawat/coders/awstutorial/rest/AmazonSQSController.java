package org.shekhawat.coders.awstutorial.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shekhawat.coders.awstutorial.service.AmazonSQSService;
import org.shekhawat.coders.awstutorial.utils.MyTuple;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sqs.model.DeleteMessageBatchResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("sqs")
@RequiredArgsConstructor
public class AmazonSQSController {

    private final AmazonSQSService amazonSQSService;

    @GetMapping
    public MyTuple.MyTuple2<List<Message>, DeleteMessageBatchResponse> consumeMessage() {
        return amazonSQSService.consume();
    }

    @PostMapping
    public SendMessageResponse produceMessage(@RequestBody String message) {
        return amazonSQSService.produce(message);
    }

    @PostMapping("bulk")
    public SendMessageBatchResponse produceBulkMessage(@RequestBody List<String> messages) {
        return amazonSQSService.produceBatch(messages);
    }
}
