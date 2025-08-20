package com.example.auction_market.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySeller_MemberId(Long memberId);
    List<Product> findByCategory(String category);
}
