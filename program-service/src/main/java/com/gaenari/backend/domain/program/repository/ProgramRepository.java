package com.gaenari.backend.domain.program.repository;

import com.gaenari.backend.domain.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    // 해당 회원의 프로그램 리스트를 즐겨찾기, 사용횟수 순으로 정렬
    List<Program> findByMemberIdOrderByIsFavoriteDescUsageCountDesc(String programId);

    // 해당 회원이 즐겨찾기한 프로그램 리스트를 사용횟수 순으로 정렬
    List<Program> findByMemberIdAndIsFavoriteOrderByUsageCountDesc(String memberId, Boolean isFavorite);

    Program findByMemberIdAndId(String memberId, Long programId);
}
