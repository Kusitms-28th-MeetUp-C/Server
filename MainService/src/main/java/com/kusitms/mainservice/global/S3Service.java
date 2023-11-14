package com.kusitms.mainservice.global;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class S3Service {
    @Value("${application.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public void deleteFile(String fileName) {
        try {
            if (s3Client.doesObjectExist(bucketName, fileName)) {
                s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
                log.info("File deleted: {}", fileName);
            } else {
                log.warn("File not found: {}", fileName);
            }
        } catch (Exception e) {
            log.error("Error deleting file: {}", fileName, e);
        }
    }

    public String uploadFile(MultipartFile file, Long memberId) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName =  Long.toString(memberId);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return "File uploaded : " + fileName;
    }
    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipart file to file", e);
        }
        return convertedFile;
    }
}
