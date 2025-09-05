package com.example.auction_market.dto.wishlistDto;

import com.example.auction_market.domain.wishlist.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponse {
    private Long wishlistId;
    private Long productId;
    private String productTitle;
    private String productCategory;
    private Long currentPrice;
    private Long buyNowPrice;
    private String thumbnailUrl;
    private String auctionStatus;
    private LocalDateTime createdAt;
    private boolean isWished;

    public static WishlistResponse fromEntity(Wishlist wishlist) {
        // 썸네일 이미지 찾기
        String thumbnailUrl = wishlist.getProduct().getImages().stream()
                .filter(img -> img.isThumbnail())
                .map(img -> img.getImageUrl())
                .findFirst()
                .orElse(wishlist.getProduct().getImages().isEmpty() ? null : 
                       wishlist.getProduct().getImages().get(0).getImageUrl());

        return WishlistResponse.builder()
                .wishlistId(wishlist.getWishlistId())
                .productId(wishlist.getProduct().getProductId())
                .productTitle(wishlist.getProduct().getTitle())
                .productCategory(wishlist.getProduct().getCategory().name())
                .currentPrice(wishlist.getProduct().getHighestPrice())
                .buyNowPrice(wishlist.getProduct().getBuyNowPrice())
                .thumbnailUrl(thumbnailUrl)
                .auctionStatus("ONGOING") // 기본값, 실제로는 Auction 엔티티에서 가져와야 함
                .createdAt(wishlist.getCreatedAt())
                .isWished(true)
                .build();
    }
}
