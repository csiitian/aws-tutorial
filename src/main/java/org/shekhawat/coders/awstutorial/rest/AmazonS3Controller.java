package org.shekhawat.coders.awstutorial.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shekhawat.coders.awstutorial.service.AmazonS3Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("s3")
@RequiredArgsConstructor
public class AmazonS3Controller {

    private final AmazonS3Service amazonS3Service;

    @GetMapping("buckets")
    public List<String> getAllBuckets() {
        return amazonS3Service.getAllBuckets();
    }

    @GetMapping("bucket/all")
    public List<String> getBucketObjects() {
        return amazonS3Service.getBucketObjects();
    }

    @PostMapping
    public List<String> uploadFile(@RequestParam("file") List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
                .map(amazonS3Service::uploadFile)
                .collect(Collectors.toList());
    }

    @PutMapping
    public String updateFile(@RequestParam String fileName, @RequestParam("file") MultipartFile multipartFile) {
        return amazonS3Service.updateFile(fileName, multipartFile);
    }

    @DeleteMapping
    public void deleteFile(@RequestParam String fileName) {
        amazonS3Service.deleteFile(fileName);
    }

    @PostMapping("multipart")
    public List<String> multipartUploadFile(@RequestParam("file") List<MultipartFile> multipartFiles) {
        log.info("Multipart Upload Started.");
        return multipartFiles.stream()
                .map(amazonS3Service::multipartUploadFile)
                .collect(Collectors.toList());
    }

    @PostMapping("download")
    public String downloadFile(@RequestParam String fileName) {
        return amazonS3Service.downloadFile(fileName);
    }
}
