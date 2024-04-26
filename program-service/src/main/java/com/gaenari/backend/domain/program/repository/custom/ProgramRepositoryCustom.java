package com.gaenari.backend.domain.program.repository.custom;

import com.gaenari.backend.domain.program.entity.Program;

import java.util.List;

public interface ProgramRepositoryCustom {

    // 프로그램 완주 횟수 카운트
    Integer countFinish(Long programId);

    // 특정 회원이 등록한 프로그램 리스트 조회
    List<Program> getProgramList(Long memberId);

    // 특정 프로그램 상세정보 조회
    Program getProgram(Long programId);

}
