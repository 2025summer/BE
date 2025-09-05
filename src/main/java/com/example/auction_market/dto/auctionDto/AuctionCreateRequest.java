package com.example.auction_market.dto.auctionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionCreateRequest {
    private Long productId;
    private Long startPrice;
    private LocalDateTime endTime;
}
