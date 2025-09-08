package com.example.auction_market.dto.auctionDto;

import com.example.auction_market.domain.auction.Auction;
import com.example.auction_market.domain.product.Product;
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
