package com.gaenari.backend.domain.favorite.service;

import com.gaenari.backend.domain.favorite.dto.responseDto.FavoriteDto;

import java.util.List;

public interface FavoriteService {

    List<FavoriteDto> getFavoriteList(String memberId);

    Boolean registerFavorite(String memberId, Long programId);

    Boolean clearFavorite(String memberId, Long programId);
}
