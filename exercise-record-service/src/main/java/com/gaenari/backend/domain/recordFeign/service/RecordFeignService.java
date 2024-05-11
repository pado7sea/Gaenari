package com.gaenari.backend.domain.recordFeign.service;

import com.gaenari.backend.domain.client.program.dto.ProgramDetailDto;

import java.util.List;

public interface RecordFeignService {

    List<ProgramDetailDto.UsageLogDto> getRecordsByProgramId(Long programId);

    List<Integer> getChallengeIdsByRecordId(String memberId, Long recordId);

}
