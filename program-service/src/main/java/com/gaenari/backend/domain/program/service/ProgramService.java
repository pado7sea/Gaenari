package com.gaenari.backend.domain.program.service;

import com.gaenari.backend.domain.program.dto.requestDto.ProgramCreateDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.domain.program.dto.responseDto.ProgramDto;

import java.util.List;

public interface ProgramService {

    /**
     * 프로그램을 생성합니다.
     *
     * @param accountId    회원 식별자
     * @param programDto  프로그램 생성 DTO
     * @return 생성된 프로그램의 식별자
     */
    Long createProgram(String accountId, ProgramCreateDto programDto);

    /**
     * 프로그램을 삭제합니다.
     *
     * @param accountId  회원 식별자
     * @param programId 프로그램 식별자
     */
    void deleteProgram(String accountId, Long programId);

    /**
     * 회원의 프로그램 목록을 가져옵니다.
     *
     * @param accountId 회원 식별자
     * @return 프로그램 목록
     */
    List<ProgramDto> getProgramList(String accountId);

    /**
     * 프로그램의 상세 정보를 가져옵니다.
     *
     * @param accountId  회원 식별자
     * @param programId 프로그램 식별자
     * @return 프로그램 상세 정보 DTO
     */
    ProgramDetailDto getProgramDetail(String accountId, Long programId);

}
