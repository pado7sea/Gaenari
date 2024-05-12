package com.gaenari.backend.domain.client;

import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import com.gaenari.backend.global.format.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "exercise-record-service", url = "${feign.exercise-record-service.url}")
public interface RecordServiceClient {

    @GetMapping("/record/feign/{programId}")
    ResponseEntity<GenericResponse<List<ProgramDetailDto.UsageLogDto>>> getUsageLog(@PathVariable(name = "programId") Long programId);
}
