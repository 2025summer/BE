package com.example.auction_market.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
public class R2Service {

    private final S3Client s3Client;

    @Value("${cloudflare.r2.bucket-name}")
    private String bucketName;

    public R2Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // 파일 업로드
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .contentType(file.getContentType())
                        .build(),
                software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes())
        );

        return getFileUrl(fileName);
    }

    // 업로드된 파일 URL 가져오기
    public String getFileUrl(String key) {
        URL url = s3Client.utilities().getUrl(
                GetUrlRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()
        );
        return url.toString();
    }
}
