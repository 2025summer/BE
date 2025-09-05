package com.example.auction_market.domain.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_image")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(length = 1000) // URL 길이를 1000자로 확장
    private String imageUrl;

    @Column(name = "is_thumbnail", nullable = false)
    @Builder.Default
    private boolean isThumbnail = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    private LocalDateTime createdAt;
}
