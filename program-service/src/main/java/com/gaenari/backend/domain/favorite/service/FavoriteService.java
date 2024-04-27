package com.gaenari.backend.domain.favorite.service;

import com.gaenari.backend.domain.favorite.dto.responseDto.FavoriteListDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FavoriteService {

    List<FavoriteListDto> getFavoriteList(Long memberId);

    Boolean registerFavorite(Long programId);

    Boolean clearFavorite(Long programId);
}
