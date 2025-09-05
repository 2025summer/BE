package com.example.auction_market.dto.auctionDto;

import com.example.auction_market.domain.auction.Auction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionResponse {
    private Long auctionId;
    private Long productId;
    private String productTitle;
    private Long startPrice;
    private Long currentPrice;
    private LocalDateTime endTime;
    private String status;
    private Integer bidCount;

    public static AuctionResponse fromEntity(Auction auction) {
        return AuctionResponse.builder()
                .auctionId(auction.getAuctionId())
                .productId(auction.getProduct().getProductId())
                .productTitle(auction.getProduct().getTitle())
                .startPrice(auction.getStartPrice())
                .currentPrice(auction.getProduct().getHighestPrice() != null ? 
                    auction.getProduct().getHighestPrice() : auction.getStartPrice())
                .endTime(auction.getEndTime())
                .status(auction.getStatus().name())
                .bidCount(auction.getProduct().getBidCount())
                .build();
    }
}
