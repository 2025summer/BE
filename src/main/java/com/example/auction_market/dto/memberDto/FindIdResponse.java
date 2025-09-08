package com.example.auction_market.dto.memberDto;

import com.example.auction_market.domain.member.Member;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindIdResponse {
    private String email;

    public static FindIdResponse fromEntity(Member member) {
        return FindIdResponse.builder()
                .email(member.getEmail())
                .build();
    }
}
