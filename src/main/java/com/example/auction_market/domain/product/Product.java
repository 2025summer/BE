package com.example.auction_market.domain.product;

import com.example.auction_market.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.chrono.JapaneseEra.values;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    private String title;

    private int bidCount;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Long highestPrice;

    private Long buyNowPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "highest_bidder_id")
    private Member highestBidder;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductImage> images = new ArrayList<>();

    public enum Category {
        ELECTRONICS,          // 전자기기
        CLOTHING,             // 의류
        FURNITURE_APPLIANCES, // 가구/가전제품
        DAILY_SUPPLIES,       // 생필품
        BEAUTY,               // 뷰티
        BOOKS,                // 책
        PET_SUPPLIES,         // 반려동물용품
        ETC,                  // 기타
        WISHLIST              // 찜 목록
    }

}
