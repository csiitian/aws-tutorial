package org.shekhawat.coders.awstutorial.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.AllArgsConstructor;
import org.shekhawat.coders.awstutorial.properties.AwsProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AmazonS3Config {

    private final AwsProperties awsProperties;

    @Bean
    public AmazonS3 s3Client(@Qualifier("awsCredentials") AWSCredentialsProvider awsCredentials) {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentials)
                .withRegion(awsProperties.getS3().getRegion())
                .build();
    }
}
