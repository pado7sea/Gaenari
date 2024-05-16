package com.gaenari.backend.domain.program.controller;

import com.gaenari.backend.domain.program.dto.responseDto.FavoriteDto;
import com.gaenari.backend.domain.program.service.FavoriteService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    private final ApiResponseCustom response;
    private final FavoriteService favoriteService;

    @Operation(summary = "즐겨찾기 목록 조회", description = "즐겨찾기 목록 조회")
    @GetMapping
    public ResponseEntity<?> getAllPrograms(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId) {
        List<FavoriteDto> favoriteList = favoriteService.getFavoriteList(accountId);

        return response.success(ResponseCode.FAVORITE_PROGRAM_LIST_FETCHED, favoriteList);
    }

    @Operation(summary = "즐겨찾기 등록/해제", description = "즐겨찾기 등록/해제")
    @PutMapping("/{programId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즐겨찾기 등록/해제 성공", content = @Content(schema = @Schema(implementation = boolean.class))),
    })
    public ResponseEntity<?> updaterFavoriteStatus(@Parameter(hidden = true) @RequestHeader("User-Info") String accountId,
                                                   @Parameter(description = "운동 프로그램 ID")  @PathVariable(name = "programId") Long programId) {
        Boolean isSuccess = favoriteService.updateFavoriteStatus(accountId, programId);

        if (isSuccess) {
            return response.success(ResponseCode.FAVORITE_PROGRAM_UPDATED, isSuccess);

        } else {
            return response.success(ResponseCode.FAVORITE_PROGRAM_DELETED, isSuccess);
        }
    }

}
