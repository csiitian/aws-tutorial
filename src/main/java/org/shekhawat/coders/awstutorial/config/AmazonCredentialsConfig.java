package org.shekhawat.coders.awstutorial.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import lombok.RequiredArgsConstructor;
import org.shekhawat.coders.awstutorial.properties.AwsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AmazonCredentialsConfig {

    private final AwsProperties awsProperties;

    @Bean("awsCredentials")
    public AWSCredentialsProvider awsCredentials() {
        return new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(
                        awsProperties.getAccess(),
                        awsProperties.getSecret()
                ));
    }
}
