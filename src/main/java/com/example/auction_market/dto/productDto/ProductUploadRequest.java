package com.example.auction_market.dto.productDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "상품 제목은 필수입니다.")
    @Size(min = 2, max = 100, message = "상품 제목은 2자 이상 100자 이하여야 합니다.")
    private String productTitle;

    @Schema(description = "상품 설명", example = "사용 1개월, 풀박스")
    @NotBlank(message = "상품 설명은 필수입니다.")
    @Size(min = 10, max = 1000, message = "상품 설명은 10자 이상 1000자 이하여야 합니다.")
    private String productDescription;

    @Schema(description = "상품 카테고리", example = "ELECTRONICS")
    @NotBlank(message = "상품 카테고리는 필수입니다.")
    private String productCategory;

    @Schema(description = "경매 시작가", example = "1000000")
    @NotNull(message = "경매 시작가는 필수입니다.")
    @Positive(message = "경매 시작가는 0보다 커야 합니다.")
    private Long startPrice;

    @Schema(description = "즉시 구매가", example = "1200000")
    @Positive(message = "즉시 구매가는 0보다 커야 합니다.")
    private Long buyNowPrice;

    @Schema(description = "경매 종료 시간", example = "2025-12-01T12:00:00")
    @NotNull(message = "경매 종료 시간은 필수입니다.")
    @Future(message = "경매 종료 시간은 현재 시간보다 미래여야 합니다.")
    private LocalDateTime auctionEndTime;
}
