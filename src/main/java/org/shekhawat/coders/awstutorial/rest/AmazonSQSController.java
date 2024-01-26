package org.shekhawat.coders.awstutorial.rest;

import com.amazonaws.services.sqs.model.DeleteMessageBatchResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageBatchResult;
import com.amazonaws.services.sqs.model.SendMessageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shekhawat.coders.awstutorial.service.AmazonSQSService;
import org.shekhawat.coders.awstutorial.utils.MyTuple;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("sqs")
@RequiredArgsConstructor
public class AmazonSQSController {

    private final AmazonSQSService amazonSQSService;

    @GetMapping
    public MyTuple.MyTuple2<List<Message>, DeleteMessageBatchResult> consumeMessage() {
        return amazonSQSService.consume();
    }

    @PostMapping
    public SendMessageResult produceMessage(@RequestBody String message) {
        return amazonSQSService.produce(message);
    }

    @PostMapping("bulk")
    public SendMessageBatchResult produceBulkMessage(@RequestBody List<String> messages) {
        return amazonSQSService.produceBatch(messages);
    }
}
