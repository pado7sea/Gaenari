package com.gaenari.backend.domain.program.service;

import com.gaenari.backend.domain.program.dto.responseDto.FavoriteDto;

import java.util.List;

public interface FavoriteService {

    List<FavoriteDto> getFavoriteList(String memberId);

    Boolean updaterFavoriteStatus(String memberId, Long programId);

}
