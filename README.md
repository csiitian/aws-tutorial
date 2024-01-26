# AWS SDK Integration in Spring Boot Application

## How to Set Up Locally
### Configuration
```
AWS_ACCESS:<<get from aws>>
AWS_SECRET:<<get from aws>>
AWS_ACCOUNT_NO:<<get from aws>>
```

Now Run [AwsTutorialApplication.java](src%2Fmain%2Fjava%2Forg%2Fshekhawat%2Fcoders%2Fawstutorial%2FAwsTutorialApplication.java)

## S3 Integration
```xml
<!-- Dependency for S3 sdk -->
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-s3</artifactId>
    <version>${aws.sdk.version}</version>
</dependency>
```
1. [Get All Buckets](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L23)
2. [Get All Objects of the Object](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L27)
3. [Upload File to S3](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L32)
4. [Delete File from S3](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L50)
5. [MultiPart Upload File to S3](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L77)
6. [Download File from S3](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonS3Service.java#L140)


## SQS Integration
```xml
<!-- Dependency for SQS sdk -->
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-sqs</artifactId>
    <version>${aws.sdk.version}</version>
</dependency>
```
1. [Consume Messages](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonSQSService.java#L25)
2. [Produce Message](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonSQSService.java#L64)
3. [Produce Bulk Message ( max size = 10 )](https://github.com/Vikasss7663/aws-tutorial/blob/e2a0fa03230c07f675dfef013c686a16a32a24fc/src/main/java/org/shekhawat/coders/awstutorial/service/AmazonSQSService.java#L49)

## Built with
[Spring Boot](https://spring.io/projects/spring-boot/),
[AWS](https://aws.amazon.com/),
[Amazon S3](https://aws.amazon.com/pm/serv-s3/)

## Contributing
Clone the repo
Create PR for your changes
