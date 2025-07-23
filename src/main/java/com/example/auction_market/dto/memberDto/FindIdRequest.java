package com.example.auction_market.dto.memberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindIdRequest {
    private String name;
    private String phoneNumber;
}
