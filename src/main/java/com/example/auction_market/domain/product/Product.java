package com.example.auction_market.domain.product;

import com.example.auction_market.domain.member.Member;
import com.example.auction_market.domain.product.*;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    private String title;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Long highestPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "highest_bidder_id")
    private Member highestBidder;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new List<ProductImage>();

    // 생성자, getter/setter
}
