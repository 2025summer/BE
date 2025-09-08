package com.example.auction_market.dto.memberDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    
    @NotBlank(message = "사용자명은 필수입니다.")
    @Size(min = 2, max = 20, message = "사용자명은 2자 이상 20자 이하여야 합니다.")
    private String username;
    
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String password;
    
    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^01[0-9]{8,9}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String phoneNumber;
    
    @NotBlank(message = "우편번호는 필수입니다.")
    private String postalCode;
    
    @NotBlank(message = "주소는 필수입니다.")
    private String address;
    
    private String addressDetail;
    private String extraAddress;
}

