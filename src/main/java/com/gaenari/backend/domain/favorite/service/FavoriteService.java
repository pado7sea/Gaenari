package com.gaenari.backend.domain.favorite.service;

import com.gaenari.backend.domain.favorite.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    // 즐겨찾기 목록 조회, 등록, 해제 기능 구현
}
