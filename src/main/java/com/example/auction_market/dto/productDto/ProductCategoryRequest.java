package com.example.auction_market.dto.productDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryRequest {
    
    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;
}
