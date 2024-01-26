package org.shekhawat.coders.awstutorial.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("aws")
public class AwsProperties {
    private String access;
    private String secret;
    private String accountNo;

    private S3Properties s3;
    private SqsProperties sqs;

    @Data
    public static class S3Properties {
        private String bucket;
        private String region;
    }

    @Data
    public static class SqsProperties {
        private String region;
        private String queueName;
        private Integer visibilityTimeout;
        private Integer maxMessage;
        private Integer waitTime;
    }
}
