package com.example.auction_market.api;

import com.example.auction_market.application.AuctionService;
import com.example.auction_market.dto.auctionDto.AuctionCreateRequest;
import com.example.auction_market.dto.auctionDto.AuctionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    /**
     * 경매 생성
     */
    @PostMapping("/create")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "경매 생성",
            description = "상품에 대한 경매를 생성합니다."
    )
    public ResponseEntity<AuctionResponse> createAuction(@RequestBody AuctionCreateRequest request) {
        return auctionService.createAuction(request);
    }

    /**
     * 진행 중인 모든 경매 조회
     */
    @GetMapping("/ongoing")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "진행 중인 경매 목록 조회",
            description = "현재 진행 중인 모든 경매를 조회합니다.",
            security = {} // 인증 없이 접근 가능
    )
    public ResponseEntity<List<AuctionResponse>> getOngoingAuctions() {
        return auctionService.getOngoingAuctions();
    }

    /**
     * 특정 경매 상세 조회
     */
    @GetMapping("/{auctionId}")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "경매 상세 조회",
            description = "특정 경매의 상세 정보를 조회합니다.",
            security = {} // 인증 없이 접근 가능
    )
    public ResponseEntity<AuctionResponse> getAuction(
            @PathVariable Long auctionId) {
        return auctionService.getAuction(auctionId);
    }

    /**
     * 경매 종료
     */
    @PutMapping("/{auctionId}/finish")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "경매 종료",
            description = "진행 중인 경매를 종료합니다. (관리자 전용)"
    )
    public ResponseEntity<String> finishAuction(@PathVariable Long auctionId) {
        auctionService.finishAuction(auctionId);
        return ResponseEntity.ok("경매가 종료되었습니다.");
    }
}
