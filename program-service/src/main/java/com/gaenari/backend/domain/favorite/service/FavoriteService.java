package com.gaenari.backend.domain.favorite.service;

import com.gaenari.backend.domain.favorite.dto.responseDto.FavoriteDto;

import java.util.List;

public interface FavoriteService {

    List<FavoriteDto> getFavoriteList(Long memberId);

    Boolean registerFavorite(Long programId);

    Boolean clearFavorite(Long programId);
}
