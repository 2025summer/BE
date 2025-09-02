package com.example.auction_market.api;

import com.example.auction_market.application.ProductService;
import com.example.auction_market.dto.productDto.ProductCategoryRequest;
import com.example.auction_market.dto.productDto.ProductCategoryResponse;
import com.example.auction_market.dto.productDto.ProductUploadRequest;
import com.example.auction_market.security.CustomUserDetails;
import jakarta.validation.Valid;
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
    @PostMapping(
            value = "/uploadProduct",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> uploadProduct(
            @RequestPart("product") @Valid ProductUploadRequest request, // JSON DTO
            @RequestPart("images") List<MultipartFile> images,          // 파일 리스트
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws IOException {

        // 로그인 여부 확인
        if (userDetails == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        // 디버깅용 로그
        System.out.println("로그인 사용자: " + userDetails.getUsername());
        System.out.println("상품명: " + request.getProductTitle());
        System.out.println("첨부 이미지 수: " + (images != null ? images.size() : 0));

        // Service 호출
        productService.uploadProductWithImages(request, images, userDetails.getMember().getMemberId());

        return ResponseEntity.ok("상품 등록 완료");
    }


    @GetMapping("/category")
    @io.swagger.v3.oas.annotations.Operation(
        summary = "카테고리별 상품 조회", 
        description = "인증 없이 카테고리별 상품 목록을 조회합니다.",
        security = {} // 이 API는 인증이 필요 없음을 명시
    )
    public ResponseEntity<List<ProductCategoryResponse>> loadCategoryProducts(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "상품 카테고리 (대소문자 구분 없음)",
            example = "ELECTRONICS"
        )
        @RequestParam String category) {
        ProductCategoryRequest request = ProductCategoryRequest.builder()
                .category(category)
                .build();
        return productService.getCategoryProducts(request);
    }
}
