package com.gaenari.backend.domain.mypet.controller;

import com.gaenari.backend.domain.mypet.dto.requestDto.Adopt;
import com.gaenari.backend.domain.mypet.dto.requestDto.HeartChange;
import com.gaenari.backend.domain.mypet.dto.requestDto.IncreaseAffection;
import com.gaenari.backend.domain.mypet.dto.responseDto.FriendPetDetail;
import com.gaenari.backend.domain.mypet.service.MyPetService;
import com.gaenari.backend.global.format.code.ErrorCode;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class MyPetController {
    private final ApiResponse response;
    private final Environment env;
    private final MyPetService myPetService;

    @Operation(summary = "반려견 입양")
    @PostMapping("/pet/adopt")
    public ResponseEntity<?> adopt(@RequestHeader("User-Info") String memberEmail, @RequestBody Adopt adopt){
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        myPetService.adopt(memberEmail, adopt);
        return response.success(ResponseCode.ADOPT_NEW_PET_SUCCESS);
    }

    @Operation(summary = "파트너 반려견 변경")
    @PutMapping("/pet/partner/{dogId}")
    public ResponseEntity<?> changePartner(@RequestHeader("User-Info") String memberEmail, @PathVariable Long dogId){
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        myPetService.changePartner(memberEmail, dogId);
        return response.success(ResponseCode.PARTNER_PET_CHANGE_SUCCESS);
    }

    @Operation(summary = "파트너 반려견 조회")
    @GetMapping("/pet/partner")
    public ResponseEntity<?> getPartner(@RequestHeader("User-Info") String memberEmail){
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        FriendPetDetail friendPetDetail = myPetService.getPartner(memberEmail);
        return response.success(ResponseCode.PARTNER_PET_GET_SUCCESS, friendPetDetail);
    }

    @Operation(summary = "반려견 애정도 증가")
    @PutMapping("/pet/heart")
    public ResponseEntity<?> increaseAffection(@RequestHeader("User-Info") String memberEmail, @RequestBody IncreaseAffection affection){
        // memberId가 null이면 인증 실패
        if (memberEmail == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        myPetService.increaseAffection(memberEmail, affection);
        return response.success(ResponseCode.PARTNER_PET_AFFECTION_INCREASE_SUCCESS);
    }

    @Operation(summary = "[Feign] 반려견 애정도 증/감", description = "Feign API")
    @PostMapping("/pet/heart/change")
    public ResponseEntity<?> changeHeart(@RequestBody HeartChange heartChange){
        // memberId가 null이면 인증 실패
        if (heartChange.getMemberEmail() == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        myPetService.changeHeart(heartChange);
        return ResponseEntity.ok().build();
    }

}
