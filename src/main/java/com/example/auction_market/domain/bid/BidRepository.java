package com.example.auction_market.domain.bid;

import com.example.auction_market.domain.auction.Auction;
import com.example.auction_market.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    // 특정 경매에서 특정 사용자가 한 모든 입찰 조회
    List<Bid> findByAuctionAndBidder(Auction auction, Member bidder);

    // 특정 경매에 대한 모든 입찰 조회 (가격 내림차순으로)
    List<Bid> findByAuctionOrderByBidPriceDesc(Auction auction);

    // 특정 사용자가 한 모든 입찰 조회
    List<Bid> findByBidder(Member bidder);
    
    // 특정 사용자가 한 모든 입찰 조회 (시간 내림차순)
    List<Bid> findByBidderOrderByBidTimeDesc(Member bidder);
}
