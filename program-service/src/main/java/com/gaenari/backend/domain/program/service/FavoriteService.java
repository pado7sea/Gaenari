package com.gaenari.backend.domain.program.service;

import com.gaenari.backend.domain.program.dto.responseDto.FavoriteDto;

import java.util.List;

public interface FavoriteService {

    /**
     * 해당 회원의 즐겨찾기한 프로그램 리스트를 반환합니다.
     *
     * @param accountId 회원 식별자
     * @return 즐겨찾기한 프로그램 리스트
     */
    List<FavoriteDto> getFavoriteList(String accountId);

    /**
     * 해당 회원의 프로그램의 즐겨찾기 상태를 업데이트하고 업데이트된 결과를 반환합니다.
     *
     * @param accountId  회원 식별자
     * @param programId 프로그램 식별자
     * @return 즐겨찾기 상태가 성공적으로 업데이트되었는지 여부
     */
    Boolean updateFavoriteStatus(String accountId, Long programId);

}