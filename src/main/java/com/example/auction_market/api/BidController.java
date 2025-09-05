package com.example.auction_market.api;

import com.example.auction_market.application.BidService;
import com.example.auction_market.dto.bidDto.BidRequest;
import com.example.auction_market.dto.bidDto.BidResponse;
import com.example.auction_market.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bids")
public class BidController {

    private final BidService bidService;

    /**
     * 입찰 등록
     */
    @PostMapping("/place")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "입찰 등록",
            description = "경매에 입찰을 등록합니다. 로그인이 필요합니다."
    )
    public ResponseEntity<BidResponse> placeBid(
            @Valid @RequestBody BidRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        if (userDetails == null) {
            return ResponseEntity.status(401).body(null);
        }
        
        return bidService.placeBid(request, userDetails.getMember().getMemberId());
    }

    /**
     * 특정 경매의 입찰 내역 조회
     */
    @GetMapping("/auction/{auctionId}")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "경매별 입찰 내역 조회",
            description = "특정 경매의 모든 입찰 내역을 조회합니다.",
            security = {} // 인증 없이 접근 가능
    )
    public ResponseEntity<List<BidResponse>> getAuctionBids(@PathVariable Long auctionId) {
        return bidService.getAuctionBids(auctionId);
    }

    /**
     * 내 입찰 내역 조회
     */
    @GetMapping("/my")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "내 입찰 내역 조회",
            description = "로그인한 회원의 모든 입찰 내역을 조회합니다."
    )
    public ResponseEntity<List<BidResponse>> getMyBids(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        if (userDetails == null) {
            return ResponseEntity.status(401).body(null);
        }
        
        return bidService.getMemberBids(userDetails.getMember().getMemberId());
    }
}
