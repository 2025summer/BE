package com.example.auction_market.dto.bidDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidRequest {
    
    @NotNull(message = "경매 ID는 필수입니다.")
    private Long auctionId;
    
    @NotNull(message = "입찰 금액은 필수입니다.")
    @Positive(message = "입찰 금액은 0보다 커야 합니다.")
    private Long bidPrice;
}
