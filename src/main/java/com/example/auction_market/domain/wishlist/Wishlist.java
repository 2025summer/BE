package com.example.auction_market.domain.wishlist;

import com.example.auction_market.domain.member.Member;
import com.example.auction_market.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "product_id"}))
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
