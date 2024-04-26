package com.gaenari.backend.domain.favorite.repository;

import com.gaenari.backend.domain.favorite.repository.custom.FavoriteRepositoryCustom;
import com.gaenari.backend.domain.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FavoriteRepository extends JpaRepository<Program, Long>, FavoriteRepositoryCustom {
}
