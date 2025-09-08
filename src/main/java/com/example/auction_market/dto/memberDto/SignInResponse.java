package com.example.auction_market.dto.memberDto;

import com.example.auction_market.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResponse {
    private String token;
    private String username;
    private String email;
    private Role role;

    public enum Role {
        USER, ADMIN
    }

    public static SignInResponse fromEntity(Member member, String token) {
        return SignInResponse.builder()
                .token(token)
                .username(member.getUsername())
                .email(member.getEmail())
                .role(Role.valueOf(member.getRole().name()))
                .build();
    }
}
