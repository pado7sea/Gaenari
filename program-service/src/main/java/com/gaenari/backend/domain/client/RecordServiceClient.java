package com.gaenari.backend.domain.client;

import com.gaenari.backend.domain.program.dto.responseDto.ProgramDetailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "exercise-record-service")
public interface RecordServiceClient {

    @GetMapping("/record/feign/{programId}")
    List<ProgramDetailDto.UsageLogDto> getUsageLog(@PathVariable(name = "programId") Long programId);
}
