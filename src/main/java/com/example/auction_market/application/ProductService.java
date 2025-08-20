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
import com.example.auction_market.dto.productDto.ProductUploadRequest;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

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
                    .isThumbnail(i == 0) // 첫 번째 이미지면 true
                    .createdAt(LocalDateTime.now())
                    .build();

            productImageRepository.save(productImage);
        }

    }

    public ResponseEntity<List<Product>> getCategoryProducts(ProductCategoryRequest request) {
        List<Product> products = productRepository.findByCategory(request.getCategory());
        return ResponseEntity.ok(products);
    }

}
