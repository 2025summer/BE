package com.example.auction_market.dto.wishlistDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistRequest {
    
    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;
}
