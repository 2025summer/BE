package com.example.auction_market.api;

import com.example.auction_market.application.WishlistService;
import com.example.auction_market.dto.wishlistDto.WishlistRequest;
import com.example.auction_market.dto.wishlistDto.WishlistResponse;
import com.example.auction_market.dto.wishlistDto.WishlistToggleResponse;
import com.example.auction_market.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    /**
     * 찜 목록 토글 (추가/제거)
     */
    @PostMapping("/toggle")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "찜 목록 토글",
            description = "상품을 찜 목록에 추가하거나 제거합니다. 로그인이 필요합니다."
    )
    public ResponseEntity<WishlistToggleResponse> toggleWishlist(
            @Valid @RequestBody WishlistRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        if (userDetails == null) {
            return ResponseEntity.status(401).body(null);
        }
        
        return wishlistService.toggleWishlist(request, userDetails.getMember().getMemberId());
    }

    /**
     * 내 찜 목록 조회
     */
    @GetMapping("/my")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "내 찜 목록 조회",
            description = "로그인한 회원의 찜 목록을 조회합니다."
    )
    public ResponseEntity<List<WishlistResponse>> getMyWishlist(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        if (userDetails == null) {
            return ResponseEntity.status(401).body(null);
        }
        
        return wishlistService.getMyWishlist(userDetails.getMember().getMemberId());
    }

    /**
     * 특정 상품의 찜 상태 확인
     */
    @GetMapping("/check/{productId}")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "상품 찜 상태 확인",
            description = "특정 상품이 찜 목록에 있는지 확인합니다."
    )
    public ResponseEntity<Boolean> isWished(
            @PathVariable Long productId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        if (userDetails == null) {
            return ResponseEntity.ok(false); // 로그인하지 않은 경우 false 반환
        }
        
        return wishlistService.isWished(productId, userDetails.getMember().getMemberId());
    }

    /**
     * 특정 상품의 총 찜 개수 조회
     */
    @GetMapping("/count/{productId}")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "상품 찜 개수 조회",
            description = "특정 상품의 총 찜 개수를 조회합니다.",
            security = {} // 인증 없이 접근 가능
    )
    public ResponseEntity<Long> getWishCount(@PathVariable Long productId) {
        return wishlistService.getWishCount(productId);
    }

    /**
     * 내 찜 개수 조회
     */
    @GetMapping("/my/count")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "내 찜 개수 조회",
            description = "로그인한 회원의 총 찜 개수를 조회합니다."
    )
    public ResponseEntity<Long> getMyWishCount(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        if (userDetails == null) {
            return ResponseEntity.status(401).body(null);
        }
        
        return wishlistService.getMyWishCount(userDetails.getMember().getMemberId());
    }

    /**
     * 특정 찜 항목 제거
     */
    @DeleteMapping("/{wishlistId}")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "찜 항목 제거",
            description = "특정 찜 항목을 제거합니다."
    )
    public ResponseEntity<String> removeWishlist(
            @PathVariable Long wishlistId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        if (userDetails == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }
        
        return wishlistService.removeWishlist(wishlistId, userDetails.getMember().getMemberId());
    }
}
