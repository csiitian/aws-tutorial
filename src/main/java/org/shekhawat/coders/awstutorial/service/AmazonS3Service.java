package org.shekhawat.coders.awstutorial.service;

import lombok.extern.slf4j.Slf4j;
import org.shekhawat.coders.awstutorial.properties.AwsProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AmazonS3Service {

    private final S3Client s3Client;
    private final AwsProperties awsProperties;

    public AmazonS3Service(@Qualifier("s3Client") S3Client s3Client, AwsProperties awsProperties) {
        this.s3Client = s3Client;
        this.awsProperties = awsProperties;
    }

    public List<String> getAllBuckets() {
        return s3Client.listBuckets().buckets().stream()
                .map(Bucket::name).toList();
    }

    public List<String> getBucketObjects() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(awsProperties.getS3().getBucket())
                .build();
        return s3Client.listObjectsV2(request).contents().stream()
                .map(S3Object::key).toList();
    }

    public String uploadFile(MultipartFile multipartFile) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsProperties.getS3().getBucket())
                    .key(multipartFile.getOriginalFilename())
                    .contentLength(multipartFile.getSize())
                    .storageClass(StorageClass.GLACIER)
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromBytes(multipartFile.getInputStream().readAllBytes()));
            return multipartFile.getOriginalFilename() + " Uploaded.";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public void deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(awsProperties.getS3().getBucket())
                .key(fileName)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    public String updateFile(String fileName, MultipartFile multipartFile) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsProperties.getS3().getBucket())
                    .key(fileName)
                    .contentLength(multipartFile.getSize())
                    .storageClass(StorageClass.GLACIER)
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromBytes(multipartFile.getInputStream().readAllBytes()));
            return multipartFile.getOriginalFilename() + " Uploaded.";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public String multipartUploadFile(MultipartFile multipartFile) {
        String bucketName = awsProperties.getS3().getBucket();
        String key = multipartFile.getOriginalFilename();

        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        CreateMultipartUploadResponse createMultipartUploadResponse = s3Client.createMultipartUpload(createMultipartUploadRequest);
        String uploadId = createMultipartUploadResponse.uploadId();
        log.info("UploadId: {}", uploadId);

        try {
            InputStream inputStream = multipartFile.getInputStream();
            int BUFFER_SIZE = 5 * 1024 * 1024, partId = 1, bytesRead;
            byte[] byteArray = new byte[BUFFER_SIZE];
            List<CompletedPart> completedParts = new ArrayList<>();

            while ((bytesRead = inputStream.read(byteArray, 0, BUFFER_SIZE)) != -1) {
                log.info("Part No. {}, Bytes Read: {}", partId, bytesRead);

                UploadPartRequest uploadRequest = UploadPartRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .uploadId(uploadId)
                        .partNumber(partId)
                        .build();

                UploadPartResponse uploadPartResponse = s3Client.uploadPart(uploadRequest,
                        RequestBody.fromByteBuffer(ByteBuffer.wrap(byteArray, 0, bytesRead)));

                completedParts.add(CompletedPart.builder()
                        .partNumber(partId)
                        .eTag(uploadPartResponse.eTag())
                        .build());

                log.info("Successfully submitted uploadPartId: {} | {}", partId++, uploadPartResponse);
            }

            CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
                    .parts(completedParts)
                    .build();

            CompleteMultipartUploadRequest completeMultipartUploadRequest = CompleteMultipartUploadRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .uploadId(uploadId)
                    .multipartUpload(completedMultipartUpload)
                    .build();

            String response = s3Client.completeMultipartUpload(completeMultipartUploadRequest).key();
            log.info("File uploaded successfully.");
            return response;
        } catch (IOException e) {
            // abort upload
            AbortMultipartUploadRequest abortMultipartUploadRequest = AbortMultipartUploadRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .uploadId(uploadId)
                    .build();
            s3Client.abortMultipartUpload(abortMultipartUploadRequest);
            return e.getMessage();
        }
    }

    public String downloadFile(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(awsProperties.getS3().getBucket())
                .key(fileName)
                .build();

        try {
            byte[] objectResponse = s3Client.getObject(getObjectRequest).readAllBytes();
            File file = new File(System.getProperty("user.dir") + "/" + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            fileOutputStream.write(objectResponse);
            fileOutputStream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
