# AWS SDK Integration in Spring Boot Application

## How to Set Up Locally
### Configuration
```
AWS_ACCESS:<<get from aws>>
AWS_SECRET:<<get from aws>>
AWS_ACCOUNT_NO:<<get from aws>>
```

Now Run [AwsTutorialApplication.java](src%2Fmain%2Fjava%2Forg%2Fshekhawat%2Fcoders%2Fawstutorial%2FAwsTutorialApplication.java)

## pom.xml file
```xml
<properties>
    <aws.sdk.version>2.23.10</aws.sdk.version>
</properties>
```

## S3 Integration
```xml
<!-- Dependency for S3 sdk -->
<dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>s3</artifactId>
    <version>${aws.sdk.version}</version>
</dependency>
```
1. [Get All Buckets](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L33)
2. [Get All Objects of the Object](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L38)
3. [Upload File to S3](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L46)
4. [Delete File from S3](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L63)
5. [Update File to S3](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L72)
6. [MultiPart Upload File to S3](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L89)
7. [Download File from S3](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L155)


## SQS Integration
```xml
<!-- Dependency for SQS sdk -->
<dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>sqs</artifactId>
    <version>${aws.sdk.version}</version>
</dependency>
```
1. [Consume Messages](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonSQSService.java#L25)
2. [Produce Message](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonSQSService.java#L52)
3. [Produce Bulk Message ( max size = 10 )](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonSQSService.java#L61)

## Built with
[Spring Boot](https://spring.io/projects/spring-boot/),
[AWS](https://aws.amazon.com/),
[Amazon S3](https://aws.amazon.com/pm/serv-s3/)

## Contributing
Clone the repo
Create PR for your changes
