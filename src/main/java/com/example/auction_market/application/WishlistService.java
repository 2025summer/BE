package com.example.auction_market.application;

import com.example.auction_market.domain.member.Member;
import com.example.auction_market.domain.member.MemberRepository;
import com.example.auction_market.domain.product.Product;
import com.example.auction_market.domain.product.ProductRepository;
import com.example.auction_market.domain.wishlist.Wishlist;
import com.example.auction_market.domain.wishlist.WishlistRepository;
import com.example.auction_market.dto.wishlistDto.WishlistRequest;
import com.example.auction_market.dto.wishlistDto.WishlistResponse;
import com.example.auction_market.dto.wishlistDto.WishlistToggleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    /**
     * 찜 목록 토글 (추가/제거)
     */
    public ResponseEntity<WishlistToggleResponse> toggleWishlist(WishlistRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 본인이 등록한 상품은 찜할 수 없음
        if (product.getSeller().getMemberId().equals(memberId)) {
            throw new IllegalStateException("본인이 등록한 상품은 찜할 수 없습니다.");
        }

        Optional<Wishlist> existingWishlist = wishlistRepository.findByMemberAndProduct(member, product);

        if (existingWishlist.isPresent()) {
            // 이미 찜한 상품이면 제거
            wishlistRepository.delete(existingWishlist.get());
            long totalCount = wishlistRepository.countByProduct(product);
            return ResponseEntity.ok(WishlistToggleResponse.removed(request.getProductId(), totalCount));
        } else {
            // 찜하지 않은 상품이면 추가
            Wishlist wishlist = Wishlist.builder()
                    .member(member)
                    .product(product)
                    .build();
            wishlistRepository.save(wishlist);
            long totalCount = wishlistRepository.countByProduct(product);
            return ResponseEntity.ok(WishlistToggleResponse.added(request.getProductId(), totalCount));
        }
    }

    /**
     * 내 찜 목록 조회
     */
    public ResponseEntity<List<WishlistResponse>> getMyWishlist(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        List<Wishlist> wishlists = wishlistRepository.findByMemberOrderByCreatedAtDesc(member);
        
        List<WishlistResponse> responses = wishlists.stream()
                .map(WishlistResponse::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    /**
     * 특정 상품의 찜 상태 확인
     */
    public ResponseEntity<Boolean> isWished(Long productId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        boolean isWished = wishlistRepository.existsByMemberAndProduct(member, product);
        return ResponseEntity.ok(isWished);
    }

    /**
     * 특정 상품의 총 찜 개수 조회
     */
    public ResponseEntity<Long> getWishCount(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        long count = wishlistRepository.countByProduct(product);
        return ResponseEntity.ok(count);
    }

    /**
     * 내 찜 개수 조회
     */
    public ResponseEntity<Long> getMyWishCount(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        long count = wishlistRepository.countByMember(member);
        return ResponseEntity.ok(count);
    }

    /**
     * 특정 찜 항목 제거
     */
    public ResponseEntity<String> removeWishlist(Long wishlistId, Long memberId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalArgumentException("찜 항목을 찾을 수 없습니다."));

        // 본인의 찜 항목인지 확인
        if (!wishlist.getMember().getMemberId().equals(memberId)) {
            throw new IllegalStateException("본인의 찜 항목만 삭제할 수 있습니다.");
        }

        wishlistRepository.delete(wishlist);
        return ResponseEntity.ok("찜 목록에서 제거되었습니다.");
    }
}
