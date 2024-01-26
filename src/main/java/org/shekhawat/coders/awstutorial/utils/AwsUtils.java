package org.shekhawat.coders.awstutorial.utils;

import org.shekhawat.coders.awstutorial.properties.AwsProperties;

public class AwsUtils {

    public static String getSQSQueueUrl(AwsProperties awsProperties) {
        return "https://sqs." + awsProperties.getSqs().getRegion()
                + ".amazonaws.com/" + awsProperties.getAccountNo()
                + "/" + awsProperties.getSqs().getQueueName();
    }
}
