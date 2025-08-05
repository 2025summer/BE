package com.example.auction_market.dto.productDto;


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
    private String productTitle;
    private String productDescription;
    private String productImageUrl;
    private String productCategory;
    private Long highestPrice;
    private Long buyNowPrice;
    private LocalDateTime endDate;
}
