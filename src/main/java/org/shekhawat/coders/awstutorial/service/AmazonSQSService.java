package org.shekhawat.coders.awstutorial.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shekhawat.coders.awstutorial.properties.AwsProperties;
import org.shekhawat.coders.awstutorial.utils.MyTuple;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.shekhawat.coders.awstutorial.utils.AwsUtils.getSQSQueueUrl;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonSQSService {

    private final AmazonSQS amazonSQSClient;
    private final AwsProperties awsProperties;

    public MyTuple.MyTuple2<List<Message>, DeleteMessageBatchResult> consume() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                .withQueueUrl(getSQSQueueUrl(awsProperties))
                .withWaitTimeSeconds(awsProperties.getSqs().getWaitTime())
                .withVisibilityTimeout(awsProperties.getSqs().getVisibilityTimeout())
                .withMaxNumberOfMessages(awsProperties.getSqs().getMaxMessage());

        List<Message> messages = amazonSQSClient.receiveMessage(receiveMessageRequest).getMessages();
        if (messages.isEmpty()) return new MyTuple.MyTuple2<>(Collections.emptyList(), null);

        List<DeleteMessageBatchRequestEntry> entries = messages.stream()
                .map(message -> new DeleteMessageBatchRequestEntry()
                        .withId(message.getMessageId())
                        .withReceiptHandle(message.getReceiptHandle()))
                .toList();

        DeleteMessageBatchRequest deleteMessageBatchRequest = new DeleteMessageBatchRequest()
                .withQueueUrl(getSQSQueueUrl(awsProperties))
                .withEntries(entries);

        DeleteMessageBatchResult deleteMessageBatchResult = amazonSQSClient.deleteMessageBatch(deleteMessageBatchRequest);
        return new MyTuple.MyTuple2<>(messages, deleteMessageBatchResult);
    }

    public SendMessageBatchResult produceBatch(List<String> messages) {
        List<SendMessageBatchRequestEntry> entries = messages.stream()
                .map(message -> new SendMessageBatchRequestEntry(
                        UUID.randomUUID().toString(),
                        message
                )).toList();

        SendMessageBatchRequest sendMessageBatchRequest = new SendMessageBatchRequest(
                getSQSQueueUrl(awsProperties),
                entries
        );

        return amazonSQSClient.sendMessageBatch(sendMessageBatchRequest);
    }

    public SendMessageResult produce(String message) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(
                getSQSQueueUrl(awsProperties),
                message
        );

        return amazonSQSClient.sendMessage(sendMessageRequest);
    }
}
