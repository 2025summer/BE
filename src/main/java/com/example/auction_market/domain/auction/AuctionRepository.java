package com.example.auction_market.domain.auction;

import com.example.auction_market.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    
    Optional<Auction> findByProduct_ProductId(Long productId);
    
    List<Auction> findByStatus(Auction.Status status);
    
    boolean existsByProductAndStatus(Product product, Auction.Status status);
    
    @Modifying
    @Query("UPDATE Auction a SET a.status = :status WHERE a.auctionId = :auctionId")
    void updateAuctionStatus(@Param("auctionId") Long auctionId, @Param("status") Auction.Status status);
}

