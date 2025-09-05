package com.example.auction_market.application;

import com.example.auction_market.domain.auction.Auction;
import com.example.auction_market.domain.auction.AuctionRepository;
import com.example.auction_market.domain.bid.Bid;
import com.example.auction_market.domain.bid.BidRepository;
import com.example.auction_market.domain.member.Member;
import com.example.auction_market.domain.member.MemberRepository;
import com.example.auction_market.domain.product.Product;
import com.example.auction_market.domain.product.ProductRepository;
import com.example.auction_market.dto.bidDto.BidRequest;
import com.example.auction_market.dto.bidDto.BidResponse;
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
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final AuctionService auctionService;

    /**
     * 입찰 등록
     */
    public ResponseEntity<BidResponse> placeBid(BidRequest request, Long memberId) {
        // 1. 경매 유효성 검증
        Auction auction = auctionRepository.findById(request.getAuctionId())
                .orElseThrow(() -> new IllegalArgumentException("경매를 찾을 수 없습니다."));

        if (!auctionService.isAuctionActive(request.getAuctionId())) {
            throw new IllegalStateException("경매가 종료되었거나 진행 중이 아닙니다.");
        }

        // 2. 입찰자 검증
        Member bidder = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        // 본인이 등록한 상품에는 입찰 불가
        if (auction.getProduct().getSeller().getMemberId().equals(memberId)) {
            throw new IllegalStateException("본인이 등록한 상품에는 입찰할 수 없습니다.");
        }

        // 3. 입찰 금액 검증
        validateBidPrice(auction, request.getBidPrice());

        // 4. 입찰 저장
        Bid bid = Bid.builder()
                .auction(auction)
                .bidder(bidder)
                .bidPrice(request.getBidPrice())
                .bidTime(LocalDateTime.now())
                .build();

        bidRepository.save(bid);

        // 5. 상품의 최고가 및 입찰자 업데이트
        updateProductHighestBid(auction.getProduct(), bid);

        // 6. 응답 생성
        boolean isHighestBid = true; // 방금 등록한 입찰이므로 최고가
        return ResponseEntity.ok(BidResponse.fromEntity(bid, isHighestBid));
    }

    /**
     * 특정 경매의 입찰 내역 조회
     */
    public ResponseEntity<List<BidResponse>> getAuctionBids(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("경매를 찾을 수 없습니다."));

        List<Bid> bids = bidRepository.findByAuctionOrderByBidPriceDesc(auction);
        
        // 최고가 입찰 확인
        Long highestPrice = auction.getProduct().getHighestPrice();
        
        List<BidResponse> responses = bids.stream()
                .map(bid -> BidResponse.fromEntity(bid, bid.getBidPrice().equals(highestPrice)))
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(responses);
    }

    /**
     * 특정 회원의 입찰 내역 조회
     */
    public ResponseEntity<List<BidResponse>> getMemberBids(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        List<Bid> bids = bidRepository.findByBidderOrderByBidTimeDesc(member);
        
        List<BidResponse> responses = bids.stream()
                .map(bid -> {
                    boolean isHighestBid = bid.getBidPrice().equals(bid.getAuction().getProduct().getHighestPrice());
                    return BidResponse.fromEntity(bid, isHighestBid);
                })
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(responses);
    }

    /**
     * 입찰 금액 유효성 검증
     */
    private void validateBidPrice(Auction auction, Long bidPrice) {
        Product product = auction.getProduct();
        
        // 시작가보다 높아야 함
        if (bidPrice < auction.getStartPrice()) {
            throw new IllegalArgumentException("입찰 금액은 시작가(" + auction.getStartPrice() + "원) 이상이어야 합니다.");
        }
        
        // 현재 최고가보다 높아야 함
        if (product.getHighestPrice() != null && bidPrice <= product.getHighestPrice()) {
            throw new IllegalArgumentException("입찰 금액은 현재 최고가(" + product.getHighestPrice() + "원)보다 높아야 합니다.");
        }
        
        // 즉시구매가 이하여야 함 (설정된 경우)
        if (product.getBuyNowPrice() != null && bidPrice > product.getBuyNowPrice()) {
            throw new IllegalArgumentException("입찰 금액은 즉시구매가(" + product.getBuyNowPrice() + "원) 이하여야 합니다.");
        }
    }

    /**
     * 상품의 최고가 및 최고 입찰자 업데이트
     */
    private void updateProductHighestBid(Product product, Bid bid) {
        productRepository.updateHighestBid(
                product.getProductId(), 
                bid.getBidPrice(), 
                bid.getBidder().getMemberId()
        );
        
        // 입찰 횟수 증가
        productRepository.incrementBidCount(product.getProductId());
    }
}
