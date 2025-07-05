package com.example.auction_market.domain.auction;

import com.example.auction_market.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auction")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionId;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long startPrice;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        READY, ONGOING, FINISHED
    }
}

