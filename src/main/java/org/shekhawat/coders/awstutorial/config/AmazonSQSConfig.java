package org.shekhawat.coders.awstutorial.config;

import lombok.AllArgsConstructor;
import org.shekhawat.coders.awstutorial.properties.AwsProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@AllArgsConstructor
public class AmazonSQSConfig {

    private final AwsProperties awsProperties;

    @Bean("sqsAsyncClient")
    public SqsAsyncClient sqsAsyncClient(@Qualifier("awsCredentials") AwsCredentialsProvider awsCredentials) {
        return SqsAsyncClient
                .builder()
                .credentialsProvider(awsCredentials)
                .region(Region.of(awsProperties.getSqs().getRegion()))
                .build();
    }

    @Bean("sqsClient")
    public SqsClient sqsClient(@Qualifier("awsCredentials") AwsCredentialsProvider awsCredentials) {
        return SqsClient
                .builder()
                .credentialsProvider(awsCredentials)
                .region(Region.of(awsProperties.getSqs().getRegion()))
                .build();
    }
}
