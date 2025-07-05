package com.example.auction_market.domain.auction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuctionResultRepository extends JpaRepository<AuctionResult, Long> {
    Optional<AuctionResult> findByAuction_AuctionId(Long auctionId);
}

