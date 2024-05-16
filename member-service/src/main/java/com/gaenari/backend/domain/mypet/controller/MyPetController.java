package com.gaenari.backend.domain.mypet.controller;

import com.gaenari.backend.domain.mypet.dto.requestDto.Adopt;
import com.gaenari.backend.domain.mypet.dto.requestDto.HeartChange;
import com.gaenari.backend.domain.mypet.dto.requestDto.IncreaseAffection;
import com.gaenari.backend.domain.mypet.dto.responseDto.FriendPetDetail;
import com.gaenari.backend.domain.mypet.dto.responseDto.Pets;
import com.gaenari.backend.domain.mypet.service.MyPetService;
import com.gaenari.backend.global.format.code.ErrorCode;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class MyPetController {
    private final ApiResponse response;
    private final Environment env;
    private final MyPetService myPetService;

    @Operation(summary = "반려견 입양")
    @PostMapping("/adopt")
    public ResponseEntity<?> adopt(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId, @RequestBody Adopt adopt){
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        myPetService.adopt(accountId, adopt);
        return response.success(ResponseCode.ADOPT_NEW_PET_SUCCESS);
    }

    @Operation(summary = "파트너 반려견 변경")
    @PutMapping("/partner/{dogId}")
    public ResponseEntity<?> changePartner(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId, @PathVariable Long dogId){
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        myPetService.changePartner(accountId, dogId);
        return response.success(ResponseCode.PARTNER_PET_CHANGE_SUCCESS);
    }

    @Operation(summary = "파트너 반려견 조회")
    @GetMapping("/partner")
    public ResponseEntity<?> getPartner(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId){
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        FriendPetDetail friendPetDetail = myPetService.getPartner(accountId);
        return response.success(ResponseCode.PARTNER_PET_GET_SUCCESS, friendPetDetail);
    }

    @Operation(summary = "반려견 애정도 증가")
    @PostMapping("/heart")
    public ResponseEntity<?> increaseAffection(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId, @RequestBody IncreaseAffection increaseAffection){
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        // 애정도가 0이상이여야함
        if(increaseAffection.getAffection() <= 0){
            return response.error(ErrorCode.NOT_REQUEST_AFFECTION.getMessage());
        }
        myPetService.increaseAffection(accountId, increaseAffection);
        return response.success(ResponseCode.PARTNER_PET_AFFECTION_INCREASE_SUCCESS);
    }

    @Operation(summary = "[Feign] 반려견 애정도 증/감", description = "Feign API")
    @PutMapping("/heart")
    public ResponseEntity<?> changeHeart(@RequestBody HeartChange heartChange){
        // accountId가 null이면 인증 실패
        if (heartChange.getAccountId() == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        myPetService.changeHeart(heartChange);
        return response.success(ResponseCode.PARTNER_PET_AFFECTION_INCREASE_SUCCESS);
    }

    @Operation(summary = "[Feign] 반려견 전체 조회", description = "Feign API")
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getPets(@PathVariable String accountId){
        // accountId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        List<Pets> petsList = myPetService.getPets(accountId);
        return response.success(ResponseCode.ALL_PET_GET_SUCCESS, petsList);
    }

    @Operation(summary = "[Feign] 강아지 가격 조회", description = "Feign API")
    @GetMapping("/dog/{dogId}")
    public ResponseEntity<?> getDogPrice(@PathVariable int dogId){
        int dogPrice = myPetService.getDogPrice(dogId);
        return response.success(ResponseCode.DOG_PRICE_GET_SUCCESS, dogPrice);
    }

}
