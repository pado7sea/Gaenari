package com.gaenari.backend.domain.mate.controller;

import com.gaenari.backend.domain.mate.dto.requestDto.MateCheck;
import com.gaenari.backend.domain.mate.dto.responseDto.ApplyMate;
import com.gaenari.backend.domain.mate.dto.responseDto.Mates;
import com.gaenari.backend.domain.mate.dto.responseDto.SearchMates;
import com.gaenari.backend.domain.mate.service.MateService;
import com.gaenari.backend.domain.member.dto.MemberDto;
import com.gaenari.backend.domain.member.service.MemberService;
import com.gaenari.backend.global.format.code.ErrorCode;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mate")
public class MateController {
    private final ApiResponse response;
    private final Environment env;
    private final MateService mateService;
    private final MemberService memberService;

    @GetMapping("/health_check1") // 연결 확인
    public String status() {
        return String.format("It's Working in Member service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @Operation(summary = "친구신청", description = "친구신청")
    @PostMapping("/add/{id}")
    public ResponseEntity<?> addMate(@RequestHeader("User-Info") String memberEmail, @PathVariable(name = "id") Long friendId) {
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        mateService.addMate(memberEmail, friendId); // (발신자, 수신자)
        return response.success(ResponseCode.APPLY_MATE_SUCCESS);
    }

    @Operation(summary = "친구신청 발신/수신목록", description = "type : sent(발신), received(수신)")
    @GetMapping("/list/{type}")
    public ResponseEntity<?> getSentMate(@RequestHeader("User-Info") String memberEmail, @PathVariable(name = "type") String type) {
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        // memberId 추출
        MemberDto mem = memberService.getMemberDetailsByEmail(memberEmail);
        Long memId = mem.getMemberId();
        // 서비스로 발신 목록 추출
        List<ApplyMate> notices = mateService.getSentMate(memId, type);

        if(type.equals("sent")){
            return response.success(ResponseCode.MATE_SENT_SUCCESS, notices);
        }else{
            return response.success(ResponseCode.MATE_RECEIVED_SUCCESS, notices);
        }
    }

    @Operation(summary = "친구신청 수락/거부", description = "true : 수락, false : 거부")
    @PostMapping("/check")
    public ResponseEntity<?> checkMate(@RequestHeader("User-Info") String memberEmail, @RequestBody MateCheck mateCheck) {
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        mateService.checkMate(mateCheck);

        if(mateCheck.getIsAccept()){
            return response.success(ResponseCode.ACCEPT_MATE_SUCCESS);
        }else{
            return response.success(ResponseCode.REJECT_MATE_SUCCESS);
        }

    }
    @Operation(summary = "친구목록조회", description = "친구목록조회")
    @GetMapping("")
    public ResponseEntity<?> getMates(@RequestHeader("User-Info") String memberEmail){
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        // memberId 추출
        MemberDto mem = memberService.getMemberDetailsByEmail(memberEmail);
        Long memId = mem.getMemberId();
        // memId와 친구인 목록 조회
        List<ApplyMate> mates = mateService.getMates(memId);

        return response.success(ResponseCode.MATES_SUCCESS, mates);
    }

    @Operation(summary = "친구삭제", description = "친구삭제")
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteMate(@RequestHeader("User-Info") String memberEmail, @PathVariable(name = "id") Long friendId){
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        // memberId 추출
        MemberDto mem = memberService.getMemberDetailsByEmail(memberEmail);
        Long memId = mem.getMemberId();

        mateService.deleteMate(memId, friendId);

        return response.success(ResponseCode.REMOVE_MATE_SUCCESS);
    }

    @Operation(summary = "친구검색", description = "친구 닉네임으로 검색")
    @GetMapping("/search")
    public ResponseEntity<?> searchMember(@RequestHeader("User-Info") String memberEmail, @RequestParam String nickName){
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        // memberId 추출
        MemberDto mem = memberService.getMemberDetailsByEmail(memberEmail);
        Long memId = mem.getMemberId();

        // 해당 검색어를 포함하는 member 조회
        List<SearchMates> members = mateService.getMembers(memId, nickName);

        return response.success(ResponseCode.SEARCH_MEMBER_SUCCESS, members);

    }


}
