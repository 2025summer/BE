package com.example.auction_market.api;

import com.example.auction_market.application.ProductService;
import com.example.auction_market.application.R2Service;
import com.example.auction_market.domain.product.Product;
import com.example.auction_market.domain.product.ProductRepository;
import com.example.auction_market.dto.productDto.ProductUploadRequest;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private  final ProductService productService;
    private  final ProductRepository productRepository;
    private  final R2Service r2Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException, java.io.IOException {
        String fileUrl = r2Service.uploadFile(file);
        return ResponseEntity.ok(fileUrl);
    }
}
