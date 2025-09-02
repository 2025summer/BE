package com.example.auction_market.api;

import com.example.auction_market.application.MemberService;
import com.example.auction_market.domain.member.MemberRepository;
import com.example.auction_market.dto.memberDto.*;
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
    @io.swagger.v3.oas.annotations.Operation(
            summary = "회원가입",
            description = "사용자가 이용을 위해 회원가입을 진행합니다.",
            security = {} // 이 API는 인증이 필요 없음을 명시
    )
    public ResponseEntity<String> signup(@RequestBody SignUpRequest request) {
        memberService.signup(request);
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/signin")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "로그인",
            description = "사용자가 이용을 위해 로그인을 진행합니다.",
            security = {} // 이 API는 인증이 필요 없음을 명시
    )
    public ResponseEntity<SignInResponse> signin(@RequestBody SignInRequest request) {
        SignInResponse response = memberService.signin(request).getBody();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/findId")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "아이디 찾기",
            description = "사용자가 아이디를 찾습니다.",
            security = {} // 이 API는 인증이 필요 없음을 명시
    )
    public ResponseEntity<String> findId(@RequestBody FindIdRequest request) {
        FindIdResponse findIdResponse = memberService.findId(request);
        return ResponseEntity.ok("회원님의 아이디 : " + findIdResponse.getEmail());
    }

    @PostMapping("/changePassword")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "비밀번호 변경",
            description = "사용자가 비밀번호를 변경합니다.",
            security = {} // 이 API는 인증이 필요 없음을 명시
    )
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request){
        memberService.changePassword(request);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    @PostMapping("/existEmail")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "이메일 중복 확인",
            description = "사용자가 사용할 수 있는 이메일인지 중복 확인합니다.",
            security = {} // 이 API는 인증이 필요 없음을 명시
    )
    public ResponseEntity<Boolean> existEmail(@RequestBody ExistEmailRequest request) {
        return ResponseEntity.ok(memberService.existEmail(request));
    }

}

