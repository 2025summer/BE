package com.example.auction_market.api;

import com.example.auction_market.application.ProductService;
import com.example.auction_market.domain.product.Product;
import com.example.auction_market.domain.product.ProductRepository;
import com.example.auction_market.dto.productDto.ProductUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private  final ProductService productService;
    private  final ProductRepository productRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProduct(@RequestBody ProductUploadRequest request){
        productService.productUpload(request);
        return ResponseEntity.ok("제품등록에 성공하였습니다.");
    }
}
