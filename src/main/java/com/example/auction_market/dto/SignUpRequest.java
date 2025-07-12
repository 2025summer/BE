package com.example.auction_market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String zipcode;
    private String address;
    private String detailAddress;
}

