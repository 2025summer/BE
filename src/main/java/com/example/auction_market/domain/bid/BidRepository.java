package com.example.auction_market.domain.bid;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByAuctionIdAndUserId(Long auctionId, Long userId);
}
