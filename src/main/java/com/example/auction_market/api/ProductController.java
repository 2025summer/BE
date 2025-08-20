package com.example.auction_market.api;

import com.example.auction_market.application.ProductService;
import com.example.auction_market.domain.product.Product;
import com.example.auction_market.dto.productDto.ProductCategoryRequest;
import com.example.auction_market.dto.productDto.ProductUploadRequest;
import com.example.auction_market.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 등록 + 이미지 업로드
     * @param request - 상품 정보
     * @param images - 업로드할 이미지 목록
     * @param userDetails - 로그인한 회원 정보
     */
    @PostMapping(value="/uplaodProduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProduct(
            @RequestPart("product") ProductUploadRequest request,
            @RequestPart("images") List<MultipartFile> images,
            @AuthenticationPrincipal CustomUserDetails userDetails // 로그인한 회원 정보
    ) throws IOException {

        // 로그인 여부 확인
        if (userDetails == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        // Service 호출
        productService.uploadProductWithImages(request, images, userDetails.getMember().getMemberId());

        return ResponseEntity.ok("상품 등록 완료");
    }

    @PostMapping("/category")
    public ResponseEntity<List<Product>> loadCategoryProducts(@RequestBody ProductCategoryRequest request) {
        return productService.getCategoryProducts(request);
    }
}
