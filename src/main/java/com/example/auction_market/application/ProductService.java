package com.example.auction_market.application;

import com.example.auction_market.domain.auction.AuctionRepository;
import com.example.auction_market.domain.bid.BidRepository;
import com.example.auction_market.domain.member.Member;
import com.example.auction_market.domain.member.MemberRepository;
import com.example.auction_market.domain.product.Product;
import com.example.auction_market.domain.product.ProductImage;
import com.example.auction_market.domain.product.ProductImageRepository;
import com.example.auction_market.domain.product.ProductRepository;
import com.example.auction_market.dto.productDto.ProductCategoryRequest;
import com.example.auction_market.dto.productDto.ProductCategoryResponse;
import com.example.auction_market.dto.productDto.ProductUploadRequest;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final MemberRepository memberRepository;
    private final R2Service r2Service;
    private final ProductImageRepository productImageRepository;

    public void uploadProductWithImages(ProductUploadRequest request, List<MultipartFile> images, Long memberId) throws IOException, java.io.IOException {
        // 1. 상품 저장
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Product product = Product.builder()
                .seller(member)
                .category(Product.Category.valueOf(request.getProductCategory().toUpperCase()))
                .title(request.getProductTitle())
                .description(request.getProductDescription())
                .highestPrice(request.getHighestPrice())
                .buyNowPrice(request.getBuyNowPrice())
                .bidCount(0)
                .build();
        productRepository.save(product);

        // 2. 이미지 업로드 & DB 저장
        for (int i = 0; i < images.size(); i++) {
            MultipartFile img = images.get(i);
            String url = r2Service.uploadFile(img); // R2 업로드 후 URL 반환

            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .imageUrl(url)
                    .isThumbnail(Boolean.valueOf(i == 0)) // 첫 번째 이미지면 true
                    .createdAt(LocalDateTime.now())
                    .build();

            productImageRepository.save(productImage);
        }

    }

    public ResponseEntity<List<ProductCategoryResponse>> getCategoryProducts(ProductCategoryRequest request) {
        try {
            // String을 ENUM으로 변환
            Product.Category category = Product.Category.valueOf(request.getCategory().toUpperCase());
            List<Product> products = productRepository.findByCategory(category);
            
            // Product 엔티티를 DTO로 변환
            List<ProductCategoryResponse> responseList = products.stream()
                    .map(ProductCategoryResponse::fromEntity)
                    .collect(Collectors.toList());
                    
            return ResponseEntity.ok(responseList);
        } catch (IllegalArgumentException e) {
            // 잘못된 카테고리 값인 경우
            System.err.println("잘못된 카테고리: " + request.getCategory());
            return ResponseEntity.badRequest().build();
        }
    }

}
