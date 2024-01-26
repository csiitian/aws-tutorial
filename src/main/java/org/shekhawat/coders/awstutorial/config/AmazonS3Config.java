package org.shekhawat.coders.awstutorial.config;

import lombok.AllArgsConstructor;
import org.shekhawat.coders.awstutorial.properties.AwsProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@AllArgsConstructor
public class AmazonS3Config {

    private final AwsProperties awsProperties;

    @Bean("s3AsyncClient")
    public S3AsyncClient s3AsyncClient(@Qualifier("awsCredentials") AwsCredentialsProvider awsCredentials) {
        return S3AsyncClient
                .builder()
                .credentialsProvider(awsCredentials)
                .region(Region.of(awsProperties.getS3().getRegion()))
                .build();
    }

    @Bean("s3Client")
    public S3Client s3Client(@Qualifier("awsCredentials") AwsCredentialsProvider awsCredentials) {
        return S3Client
                .builder()
                .credentialsProvider(awsCredentials)
                .region(Region.of(awsProperties.getS3().getRegion()))
                .build();
    }
}
