package org.shekhawat.coders.awstutorial.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shekhawat.coders.awstutorial.properties.AwsProperties;
import org.shekhawat.coders.awstutorial.utils.MyTuple;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.shekhawat.coders.awstutorial.utils.AwsUtils.getSQSQueueUrl;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonSQSService {

    private final SqsClient sqsClient;
    private final AwsProperties awsProperties;

    public MyTuple.MyTuple2<List<Message>, DeleteMessageBatchResponse> consume() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(getSQSQueueUrl(awsProperties))
                .waitTimeSeconds(awsProperties.getSqs().getWaitTime())
                .visibilityTimeout(awsProperties.getSqs().getVisibilityTimeout())
                .maxNumberOfMessages(awsProperties.getSqs().getMaxMessage())
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
        if (messages.isEmpty()) return new MyTuple.MyTuple2<>(Collections.emptyList(), null);

        List<DeleteMessageBatchRequestEntry> entries = messages.stream()
                .map(message -> DeleteMessageBatchRequestEntry.builder()
                        .id(message.messageId())
                        .receiptHandle(message.receiptHandle())
                        .build())
                .toList();

        DeleteMessageBatchRequest deleteMessageBatchRequest = DeleteMessageBatchRequest.builder()
                .queueUrl(getSQSQueueUrl(awsProperties))
                .entries(entries)
                .build();

        DeleteMessageBatchResponse deleteMessageBatchResponse = sqsClient.deleteMessageBatch(deleteMessageBatchRequest);
        return new MyTuple.MyTuple2<>(messages, deleteMessageBatchResponse);
    }

    public SendMessageResponse produce(String message) {
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(getSQSQueueUrl(awsProperties))
                .messageBody(message)
                .build();

        return sqsClient.sendMessage(sendMessageRequest);
    }

    public SendMessageBatchResponse produceBatch(List<String> messages) {
        List<SendMessageBatchRequestEntry> entries = messages.stream()
                .map(message -> SendMessageBatchRequestEntry.builder()
                        .id(UUID.randomUUID().toString())
                        .messageBody(message)
                        .build()
                ).toList();

        SendMessageBatchRequest sendMessageBatchRequest = SendMessageBatchRequest.builder()
                .queueUrl(getSQSQueueUrl(awsProperties))
                .entries(entries)
                .build();

        return sqsClient.sendMessageBatch(sendMessageBatchRequest);
    }
}
