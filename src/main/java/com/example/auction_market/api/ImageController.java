package com.example.auction_market.api;

import com.example.auction_market.application.R2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final R2Service r2Service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> uploadImage(@RequestPart("file") MultipartFile file) throws IOException {
        System.out.println("업로드 요청 수신");

        String key = r2Service.uploadFile(file);  // 업로드
        String url = r2Service.getFileUrl(key);   // URL 생성

        return Map.of(
                "key", key,
                "url", url
        );
    }
}

