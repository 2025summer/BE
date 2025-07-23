package com.example.auction_market.dto.memberDto;

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

}
