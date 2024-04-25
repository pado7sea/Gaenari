package com.gaenari.backend.domain.member.controller;

import com.gaenari.backend.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-service")
public class MemberController {
    private final Environment env;
    private final MemberService memberService;

    @GetMapping("/health_check") // 연결 확인
    public String status() {
        return String.format("It's Working in Member service on PORT %s",
                env.getProperty("local.server.port"));
    }

//    @Operation(summery = "회원가입", description = "회원가입")
//    @PostMapping("/member/sign-up")
//    public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto){
//
//    }
}
