package com.example.auction_market.dto.productDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUploadRequest {

    @Schema(description = "상품 제목", example = "아이폰 14 Pro")
    private String productTitle;

    @Schema(description = "상품 설명", example = "사용 1개월, 풀박스")
    private String productDescription;

    @Schema(description = "상품 카테고리", example = "ELECTRONICS")
    private String productCategory;

    @Schema(description = "경매 시작가", example = "1000000")
    private Long startPrice;

    @Schema(description = "즉시 구매가", example = "1200000")
    private Long buyNowPrice;

    @Schema(description = "경매 종료 시간", example = "2025-12-01T12:00:00")
    private LocalDateTime auctionEndTime;
}
