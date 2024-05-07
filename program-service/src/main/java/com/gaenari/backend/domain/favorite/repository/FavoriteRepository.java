package com.gaenari.backend.domain.favorite.repository;

import com.gaenari.backend.domain.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Program, Long> {

    List<Program> findByMemberId(String memberId);

}
