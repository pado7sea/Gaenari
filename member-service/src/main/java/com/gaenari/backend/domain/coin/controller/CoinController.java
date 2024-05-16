package com.gaenari.backend.domain.coin.controller;

import com.gaenari.backend.domain.coin.dto.requestDto.MemberCoin;
import com.gaenari.backend.domain.coin.dto.requestDto.MemberCoinIncrease;
import com.gaenari.backend.domain.coin.dto.responseDto.MemberCoinHistory;
import com.gaenari.backend.domain.coin.service.CoinService;
import com.gaenari.backend.domain.member.service.MemberService;
import com.gaenari.backend.global.format.code.ErrorCode;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coin")
public class CoinController {
    private final ApiResponse response;
    private final CoinService coinService;

    @Operation(summary = "회원보유코인 조회", description = "회원보유코인 조회")
    @GetMapping("")
    public ResponseEntity<?> getCoin(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId) {
        // memberId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        int Coin = coinService.getCoin(accountId);
        return response.success(ResponseCode.COIN_FETCH_SUCCESS, Coin);
    }

    @Operation(summary = "회원코인 내역조회", description = "회원코인 내역조회")
    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> getCoin(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                     @PathVariable(name = "year") int year,
                                     @PathVariable(name = "month") int month) {
        // memberId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        MemberCoinHistory memberCoinHistory = coinService.getCoinRecord(accountId, year, month);
        return response.success(ResponseCode.COIN_FETCH_SUCCESS, memberCoinHistory);
    }

    @Operation(summary = "회원 코인 증/감", description = "회원 코인 증/감")
    @PostMapping("")
    public ResponseEntity<?> createCoin(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                        @RequestBody MemberCoinIncrease memberCoinIncrease){
        // memberId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        // MemberCoin Dto로 변환
        MemberCoin memberCoin = MemberCoin.builder()
                .accountId(accountId)
                .isIncreased(memberCoinIncrease.getIsIncreased())
                .coinTitle(memberCoinIncrease.getCoinTitle())
                .coinAmount(memberCoinIncrease.getCoinAmount())
                .build();
        coinService.updateCoin(memberCoin);
        return response.success(ResponseCode.COIN_UPDATE_SUCCESS);
    }

    @Operation(summary = "[Feign] 회원 코인 증/감", description = "Feign API")
    @PutMapping("")
    public ResponseEntity<?> updateCoin(@RequestBody MemberCoin memberCoin){
        // memberId가 null이면 인증 실패
        if (memberCoin.getAccountId() == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        coinService.updateCoin(memberCoin);
        return response.success(ResponseCode.COIN_UPDATE_SUCCESS);
    }

    @Operation(summary = "[Feign] 회원보유코인 조회", description = "Feign API")
    @GetMapping("/{memberEmail}")
    public ResponseEntity<?> getMemberCoin(@PathVariable String accountId) {
        // memberId가 null이면 인증 실패
        if (accountId == null) {
            return response.error(ErrorCode.EMPTY_MEMBER.getMessage());
        }
        int coin = coinService.getCoin(accountId);
        return response.success(ResponseCode.COIN_FETCH_SUCCESS, coin);
    }
}
