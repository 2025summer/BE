package com.example.auction_market.domain.auction;

import com.example.auction_market.domain.member.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auction_result")
public class AuctionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @OneToOne
    @JoinColumn(name = "auction_id", unique = true)
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Member winner;

    private Long winningPrice;

    private LocalDateTime winningTime;
}

