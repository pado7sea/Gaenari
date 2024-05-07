package com.gaenari.backend.domain.program.repository.custom;

import com.gaenari.backend.domain.program.entity.Program;

import java.util.List;

public interface ProgramRepositoryCustom {

    // 특정 회원이 등록한 프로그램 리스트 조회
    List<Program> getProgramList(Long memberId);

}
