package com.gaenari.backend.domain.afterExercise.service;

import com.gaenari.backend.domain.afterExercise.dto.requestDto.NoticeInfoDto;
import com.gaenari.backend.domain.afterExercise.dto.requestDto.SaveExerciseRecordDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;

public interface AfterExerciseService {

    /**
     * 프로그램 사용 기록을 1 증가시킵니다.
     *
     * @param accountId 사용자의 식별자입니다.
     * @param exerciseDto 운동 기록에 대한 상세 정보를 담은 DTO입니다.
     */
    void updateProgramUsageCount(String accountId, SaveExerciseRecordDto exerciseDto);

    /**
     * 사용자의 운동 기록을 저장합니다.
     *
     * @param accountId 사용자의 식별자입니다.
     * @param exerciseDto 운동 기록에 대한 상세 정보를 담은 DTO입니다.
     * @return 저장된 운동 기록의 식별자를 반환합니다.
     */
    Long saveExerciseRecord(String accountId, SaveExerciseRecordDto exerciseDto);

    /**
     * 사용자의 누적 통계를 업데이트합니다.
     *
     * @param accountId 사용자의 식별자입니다.
     * @param exerciseDto 운동 기록에 대한 상세 정보를 담은 DTO입니다.
     * @return 업데이트된 누적 통계 DTO를 반환합니다.
     */
    TotalStatisticDto updateExerciseStatistics(String accountId, SaveExerciseRecordDto exerciseDto);

    void sendStartFcmNotice(String accountId, NoticeInfoDto infoDto);
}