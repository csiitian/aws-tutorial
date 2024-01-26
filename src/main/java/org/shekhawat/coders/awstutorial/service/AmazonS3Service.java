package org.shekhawat.coders.awstutorial.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shekhawat.coders.awstutorial.properties.AwsProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonS3Service {

    private final AmazonS3 amazonS3Client;
    private final AwsProperties awsProperties;

    public List<Bucket> getAllBuckets() {
        return amazonS3Client.listBuckets();
    }

    public List<S3ObjectSummary> getBucketObjects() {
        return amazonS3Client.listObjectsV2(awsProperties.getS3().getBucket())
                .getObjectSummaries();
    }

    public PutObjectResult uploadFile(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    awsProperties.getS3().getBucket(),
                    multipartFile.getOriginalFilename(),
                    multipartFile.getInputStream(),
                    objectMetadata
            ).withStorageClass(StorageClass.IntelligentTiering);

            return amazonS3Client.putObject(putObjectRequest);
        } catch (IOException e) {
            return null;
        }
    }

    public void deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
                awsProperties.getS3().getBucket(),
                fileName
        );

        amazonS3Client.deleteObject(deleteObjectRequest);
    }

    public PutObjectResult updateFile(String fileName, MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    awsProperties.getS3().getBucket(),
                    fileName,
                    multipartFile.getInputStream(),
                    objectMetadata
            );

            return amazonS3Client.putObject(putObjectRequest);
        } catch (IOException e) {
            return null;
        }
    }

    public CompleteMultipartUploadResult multipartUploadFile(MultipartFile multipartFile) {

        String bucketName = awsProperties.getS3().getBucket();
        String key = multipartFile.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());

        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(
                bucketName,
                key,
                objectMetadata
        );
        InitiateMultipartUploadResult initiateMultipartUploadResult = amazonS3Client.initiateMultipartUpload(initiateMultipartUploadRequest);
        String uploadId = initiateMultipartUploadResult.getUploadId();
        log.info("UploadId: {}", uploadId);

        try {
            InputStream inputStream = multipartFile.getInputStream();
            int BUFFER_SIZE = 5 * 1024 * 1024, partId = 1, bytesRead;
            boolean finalPart;
            byte[] byteArray = new byte[BUFFER_SIZE];
            List<PartETag> partETagList = new ArrayList<>();
            while ((bytesRead = inputStream.read(byteArray, 0, BUFFER_SIZE)) != -1) {
                finalPart = bytesRead < BUFFER_SIZE;
                log.info("Part No. {}, Bytes Read: {}", partId, bytesRead);

                UploadPartRequest uploadRequest = new UploadPartRequest()
                        .withBucketName(bucketName)
                        .withKey(key)
                        .withUploadId(uploadId)
                        .withPartNumber(partId) // partNumber should be between 1 and 10000 inclusively
                        .withPartSize(bytesRead)
                        .withInputStream(new ByteArrayInputStream(byteArray, 0, bytesRead))
                        .withLastPart(finalPart);

                UploadPartResult uploadResult = amazonS3Client.uploadPart(uploadRequest);
                partETagList.add(uploadResult.getPartETag());

                log.info("Successfully submitted uploadPartId: {} | {}", partId++, uploadResult);
            }

            log.info("File uploaded successfully.");

            CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest();
            completeMultipartUploadRequest.setKey(key);
            completeMultipartUploadRequest.setBucketName(bucketName);
            completeMultipartUploadRequest.setUploadId(uploadId);
            completeMultipartUploadRequest.setPartETags(partETagList);

            return amazonS3Client.completeMultipartUpload(completeMultipartUploadRequest);
        } catch (IOException e) {
            // abort upload
            AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest(
                    bucketName,
                    key,
                    uploadId
            );
            amazonS3Client.abortMultipartUpload(abortMultipartUploadRequest);
            return null;
        }
    }

    public String downloadFile(String fileName) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(awsProperties.getS3().getBucket(), fileName);
        try {
            byte[] objectResponse = amazonS3Client.getObject(getObjectRequest).getObjectContent().readAllBytes();
            File file = new File(System.getProperty("user.dir") + "/" + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(objectResponse);
            fileOutputStream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
