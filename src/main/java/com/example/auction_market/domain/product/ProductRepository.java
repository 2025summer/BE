package com.example.auction_market.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySeller_MemberId(Long memberId);
    List<Product> findByCategory(Product.Category category);
    
    @Modifying
    @Query("UPDATE Product p SET p.highestPrice = :price, p.highestBidder.memberId = :bidderId WHERE p.productId = :productId")
    void updateHighestBid(@Param("productId") Long productId, @Param("price") Long price, @Param("bidderId") Long bidderId);
    
    @Modifying
    @Query("UPDATE Product p SET p.bidCount = p.bidCount + 1 WHERE p.productId = :productId")
    void incrementBidCount(@Param("productId") Long productId);
}
