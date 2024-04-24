package com.gaenari.backend.domain.program.repository.custom;

import com.gaenari.backend.domain.program.dto.enumType.ProgramType;
import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramListDto;

import java.util.List;
import java.util.Optional;

public interface ProgramRepositoryCustom {

    // 프로그램 완주 횟수 카운트
    Optional<Integer> countFinish(Long programId);
    
    // 특정 회원이 등록한 프로그램 리스트 조회
    Optional<List<ProgramListDto>> getProgramList(Long memberId);
    
    // 특정 프로그램 상세정보 조회
    Optional<ProgramDetailDto> getProgramDetail(Long programId);

    // 가장 인기 있는 프로그램 리스트 조회
    Optional<List<ProgramListDto>> getPopularPrograms(int limit);

    // 프로그램 검색 기능 (키워드, 타입 등에 따라)
    Optional<List<ProgramListDto>> searchPrograms(String keyword, ProgramType type);

}
