package com.gaenari.backend.domain.favorite.controller;

import com.gaenari.backend.domain.favorite.dto.responseDto.FavoriteListDto;
import com.gaenari.backend.domain.favorite.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Favorite Program Controller", description = "Favorite Program Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/program-service/program/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "즐겨찾기 목록 조회", description = "즐겨찾기 목록 조회")
    @GetMapping
    public ResponseEntity<List<FavoriteListDto>> getAllPrograms() {
        Long memberId = 1L;
        List<FavoriteListDto> favoriteList = favoriteService.getFavoriteList(memberId);

        return ResponseEntity.ok(favoriteList);
    }

}
