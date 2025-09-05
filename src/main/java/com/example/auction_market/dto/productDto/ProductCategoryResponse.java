package com.example.auction_market.dto.productDto;

import com.example.auction_market.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryResponse {
    
    private Long productId;
    private String title;
    private String description;
    private Long highestPrice;
    private Long buyNowPrice;
    private int bidCount;
    private String category;
    private String sellerNickname;
    private List<ProductImageInfo> images;
    private Long wishCount; // 찜 개수
    private boolean isWished; // 현재 사용자의 찜 여부 (로그인한 경우만)
    
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductImageInfo {
        private Long imageId;
        private String imageUrl;
        private boolean isThumbnail;
    }
    
    // Product 엔티티에서 DTO로 변환하는 정적 메서드 (찜 정보 없음)
    public static ProductCategoryResponse fromEntity(Product product) {
        return fromEntity(product, 0L, false);
    }
    
    // Product 엔티티에서 DTO로 변환하는 정적 메서드 (찜 정보 포함)
    public static ProductCategoryResponse fromEntity(Product product, Long wishCount, boolean isWished) {
        return ProductCategoryResponse.builder()
                .productId(product.getProductId())
                .title(product.getTitle())
                .description(product.getDescription())
                .highestPrice(product.getHighestPrice())
                .buyNowPrice(product.getBuyNowPrice())
                .bidCount(product.getBidCount())
                .category(product.getCategory().name())
                .sellerNickname(product.getSeller() != null ? product.getSeller().getNickname() : null)
                .images(product.getImages().stream()
                        .map(image -> ProductImageInfo.builder()
                                .imageId(image.getImageId())
                                .imageUrl(image.getImageUrl())
                                .isThumbnail(image.isThumbnail())
                                .build())
                        .collect(Collectors.toList()))
                .wishCount(wishCount)
                .isWished(isWished)
                .build();
    }
}
