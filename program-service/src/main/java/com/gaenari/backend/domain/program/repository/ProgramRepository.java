package com.gaenari.backend.domain.program.repository;

import com.gaenari.backend.domain.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    /**
     * 해당 회원의 프로그램 리스트를 즐겨찾기 여부와 사용횟수를 기준으로 내림차순으로 정렬하여 반환합니다.
     *
     * @param programId 회원의 ID
     * @return 프로그램 리스트
     */
    List<Program> findByMemberIdOrderByIsFavoriteDescUsageCountDesc(String programId);

    /**
     * 해당 회원이 즐겨찾기한 프로그램 리스트를 사용횟수를 기준으로 내림차순으로 정렬하여 반환합니다.
     *
     * @param memberId   회원의 ID
     * @param isFavorite 즐겨찾기 여부
     * @return 프로그램 리스트
     */
    List<Program> findByMemberIdAndIsFavoriteOrderByUsageCountDesc(String memberId, Boolean isFavorite);

    /**
     * 해당 회원의 ID와 프로그램의 ID를 기준으로 프로그램을 찾아 반환합니다.
     *
     * @param memberId  회원의 ID
     * @param programId 프로그램의 ID
     * @return 프로그램 객체
     */
    Program findByMemberIdAndId(String memberId, Long programId);

}