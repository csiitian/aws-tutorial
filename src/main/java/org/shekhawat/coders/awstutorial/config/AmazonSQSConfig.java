package org.shekhawat.coders.awstutorial.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import lombok.AllArgsConstructor;
import org.shekhawat.coders.awstutorial.properties.AwsProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AmazonSQSConfig {

    private final AwsProperties awsProperties;

    @Bean
    public AmazonSQS sqsClient(@Qualifier("awsCredentials") AWSCredentialsProvider awsCredentials) {
        return AmazonSQSClient.builder()
                .withCredentials(awsCredentials)
                .withRegion(awsProperties.getSqs().getRegion())
                .build();
    }
}
