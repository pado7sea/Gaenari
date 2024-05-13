package com.gaenari.backend.domain.programFeign.service;

import com.gaenari.backend.domain.programFeign.dto.ProgramDetailAboutRecordDto;

public interface ProgramFeignService {

    /**
     * 주어진 프로그램 ID에 대한 프로그램 상세 정보를 가져옵니다.
     *
     * @param programId 프로그램 식별자
     * @return 프로그램 상세 정보 DTO
     */
    ProgramDetailAboutRecordDto getProgramDetailById(Long programId);

    /**
     * 주어진 프로그램 ID에 해당하는 프로그램의 사용 횟수를 업데이트합니다.
     *
     * @param programId 프로그램 식별자
     * @return 업데이트된 사용 횟수
     */
    Integer updateProgramUsageCount(Long programId);

}
