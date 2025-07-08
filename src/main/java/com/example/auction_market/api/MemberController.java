package com.example.auction_market.api;

import com.example.auction_market.application.MemberService;
import com.example.auction_market.domain.member.MemberRepository;
import com.example.auction_market.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest request) {
        memberService.signup(request);
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody SignInRequest request) {
        memberService.signin(request);
        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/findId")
    public ResponseEntity<String> findId(@RequestBody FindIdRequest request) {
        FindIdResponse findIdResponse = memberService.findId(request);
        return ResponseEntity.ok("회원님의 아이디 : " + findIdResponse.getEmail());
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request){
        memberService.changePassword(request);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }
}

