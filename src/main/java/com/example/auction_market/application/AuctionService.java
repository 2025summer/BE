package com.example.auction_market.application;

import com.example.auction_market.domain.auction.Auction;
import com.example.auction_market.domain.auction.AuctionRepository;
import com.example.auction_market.domain.product.Product;
import com.example.auction_market.domain.product.ProductRepository;
import com.example.auction_market.dto.auctionDto.AuctionCreateRequest;
import com.example.auction_market.dto.auctionDto.AuctionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ProductRepository productRepository;

    /**
     * 상품에 대한 경매 생성
     */
    public Auction createAuction(Product product, Long startPrice, LocalDateTime endTime) {
        Auction auction = Auction.builder()
                .product(product)
                .startPrice(startPrice)
                .endTime(endTime)
                .status(Auction.Status.ONGOING)
                .build();

        return auctionRepository.save(auction);
    }

    /**
     * 경매 생성 (외부 API 호출용)
     */
    public ResponseEntity<AuctionResponse> createAuction(AuctionCreateRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 이미 경매가 진행 중인 상품인지 확인
        if (auctionRepository.existsByProductAndStatus(product, Auction.Status.ONGOING)) {
            throw new IllegalStateException("이미 진행 중인 경매가 있습니다.");
        }

        Auction auction = createAuction(product, request.getStartPrice(), request.getEndTime());
        
        return ResponseEntity.ok(AuctionResponse.fromEntity(auction));
    }

    /**
     * 진행 중인 모든 경매 조회
     */
    public ResponseEntity<List<AuctionResponse>> getOngoingAuctions() {
        List<Auction> auctions = auctionRepository.findByStatus(Auction.Status.ONGOING);
        
        List<AuctionResponse> responses = auctions.stream()
                .map(AuctionResponse::fromEntity)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(responses);
    }

    /**
     * 특정 경매 조회
     */
    public ResponseEntity<AuctionResponse> getAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("경매를 찾을 수 없습니다."));
                
        return ResponseEntity.ok(AuctionResponse.fromEntity(auction));
    }

    /**
     * 경매 종료
     */
    public void finishAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("경매를 찾을 수 없습니다."));

        if (auction.getStatus() != Auction.Status.ONGOING) {
            throw new IllegalStateException("진행 중인 경매가 아닙니다.");
        }

        auctionRepository.updateAuctionStatus(auctionId, Auction.Status.FINISHED);
    }

    /**
     * 경매 상태 확인 (입찰 가능 여부)
     */
    public boolean isAuctionActive(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("경매를 찾을 수 없습니다."));

        return auction.getStatus() == Auction.Status.ONGOING && 
               auction.getEndTime().isAfter(LocalDateTime.now());
    }
}
