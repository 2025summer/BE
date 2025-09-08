package com.example.auction_market.application;

import com.example.auction_market.security.token.JwtUtil;
import com.example.auction_market.domain.member.Address;
import com.example.auction_market.domain.member.AddressRepository;
import com.example.auction_market.domain.member.Member;
import com.example.auction_market.domain.member.MemberRepository;
import com.example.auction_market.dto.memberDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final AddressRepository addressRepository;

    public boolean existEmail(ExistEmailRequest email) {
        return memberRepository.existsByEmail(String.valueOf(email));
    }

    public void signup(SignUpRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Member member = Member.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(UUID.randomUUID().toString().substring(0, 8))
                .phoneNumber(request.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .role(Member.Role.USER)
                .build();

        Address address = Address.builder()
                .address(request.getAddress())
                .postalCode(request.getPostalCode())
                .extraAddress(request.getExtraAddress())
                .addressDetail(request.getAddressDetail())
                .build();

        memberRepository.save(member);
        addressRepository.save(address);
    }

    public ResponseEntity<SignInResponse> signin(SignInRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if(!member.getPassword().equals(request.getPassword())) {
            throw new  IllegalArgumentException("일치하지 않는 비밀번호입니다.");
        }

        String token = jwtUtil.generateToken(member.getEmail(), member.getRole().name());
        SignInResponse signInResponse = SignInResponse.fromEntity(member, token);

        return ResponseEntity.ok(signInResponse);
    }

    public FindIdResponse findId(FindIdRequest request) {
        Member member = memberRepository.findByUsernameAndPhoneNumber(request.getName(), request.getPhoneNumber());

        return FindIdResponse.fromEntity(member);
    }

    public void changePassword(ChangePasswordRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if(!member.getPassword().equals(request.getPassword())) {
            throw new  IllegalArgumentException("기존 비밀번호와 일치하지 않습니다. 다시 입력해주세요");
        }

        if(!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new  IllegalArgumentException("새로운 비밀번호와 확인 비밀번호가 일치하지 않습니다. 다시 입력해주세요");
        }

        memberRepository.updatePasswordByEmail(request.getEmail(), request.getNewPassword());
    }
}


