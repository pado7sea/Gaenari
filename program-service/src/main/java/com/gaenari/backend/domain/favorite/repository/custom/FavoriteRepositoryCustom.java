package com.gaenari.backend.domain.favorite.repository.custom;

import com.gaenari.backend.domain.program.entity.Program;

import java.util.List;

public interface FavoriteRepositoryCustom {


    // 특정 회원이 등록한 즐겨찾기 프로그램 리스트 조회
    List<Program> getFavoriteList(Long memberId);

}
