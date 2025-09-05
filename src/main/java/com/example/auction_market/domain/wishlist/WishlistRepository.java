package com.example.auction_market.domain.wishlist;

import com.example.auction_market.domain.member.Member;
import com.example.auction_market.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    
    // 특정 회원의 찜 목록 조회
    List<Wishlist> findByMemberOrderByCreatedAtDesc(Member member);
    
    // 특정 회원이 특정 상품을 찜했는지 확인
    Optional<Wishlist> findByMemberAndProduct(Member member, Product product);
    
    // 특정 회원이 특정 상품을 찜했는지 여부 확인
    boolean existsByMemberAndProduct(Member member, Product product);
    
    // 특정 상품의 찜 개수 조회
    long countByProduct(Product product);
    
    // 특정 회원의 찜 개수 조회
    long countByMember(Member member);
    
    // 특정 상품을 찜한 회원들 조회
    @Query("SELECT w FROM Wishlist w JOIN FETCH w.member WHERE w.product = :product")
    List<Wishlist> findByProductWithMember(@Param("product") Product product);
}
