package com.example.auction_market.dto.wishlistDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistToggleResponse {
    private Long productId;
    private boolean isWished;
    private String message;
    private Long totalWishCount;

    public static WishlistToggleResponse added(Long productId, Long totalCount) {
        return WishlistToggleResponse.builder()
                .productId(productId)
                .isWished(true)
                .message("찜 목록에 추가되었습니다.")
                .totalWishCount(totalCount)
                .build();
    }

    public static WishlistToggleResponse removed(Long productId, Long totalCount) {
        return WishlistToggleResponse.builder()
                .productId(productId)
                .isWished(false)
                .message("찜 목록에서 제거되었습니다.")
                .totalWishCount(totalCount)
                .build();
    }
}
