package com.gaenari.backend.domain.favorite.controller;

import com.gaenari.backend.domain.favorite.dto.responseDto.FavoriteDto;
import com.gaenari.backend.domain.favorite.service.FavoriteService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Favorite Program Controller", description = "Favorite Program Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/program/favorite")
public class FavoriteController {

    private final ApiResponse response;
    private final FavoriteService favoriteService;

    @Operation(summary = "즐겨찾기 목록 조회", description = "즐겨찾기 목록 조회")
    @GetMapping
    public ResponseEntity<?> getAllPrograms(@Parameter(description = "회원 식별자 아이디") @RequestHeader("User-Info") Long memberId) {
        List<FavoriteDto> favoriteList = favoriteService.getFavoriteList(memberId);

        return response.success(ResponseCode.FAVORITE_PROGRAM_LIST_FETCHED, favoriteList);
    }

    @Operation(summary = "즐겨찾기 등록", description = "즐겨찾기 등록")
    @PutMapping("/{programId}")
    public ResponseEntity<?> registerFavorite(@Parameter(description = "회원 식별자 아이디") @RequestHeader("User-Info") Long memberId, @PathVariable(name = "programId") Long programId) {
        Boolean isSuccess = favoriteService.registerFavorite(programId);

        return response.success(ResponseCode.FAVORITE_PROGRAM_UPDATED, isSuccess);
    }

    @Operation(summary = "즐겨찾기 해제", description = "즐겨찾기 해제")
    @DeleteMapping("/{programId}")
    public ResponseEntity<?> clearFavorite(@Parameter(description = "회원 식별자 아이디") @RequestHeader("User-Info") Long memberId, @PathVariable(name = "programId") Long programId) {
        Boolean isSuccess = favoriteService.clearFavorite(programId);

        return response.success(ResponseCode.FAVORITE_PROGRAM_DELETED, isSuccess);
    }


}
