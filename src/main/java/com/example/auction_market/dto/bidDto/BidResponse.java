package com.example.auction_market.dto.bidDto;

import com.example.auction_market.domain.bid.Bid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidResponse {
    private Long bidId;
    private Long auctionId;
    private String bidderName;
    private Long bidPrice;
    private LocalDateTime bidTime;
    private boolean isHighestBid;

    public static BidResponse fromEntity(Bid bid, boolean isHighestBid) {
        return BidResponse.builder()
                .bidId(bid.getBidId())
                .auctionId(bid.getAuction().getAuctionId())
                .bidderName(bid.getBidder().getNickname())
                .bidPrice(bid.getBidPrice())
                .bidTime(bid.getBidTime())
                .isHighestBid(isHighestBid)
                .build();
    }
}
