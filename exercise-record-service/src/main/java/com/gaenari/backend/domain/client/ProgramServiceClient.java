package com.gaenari.backend.domain.client;

import com.gaenari.backend.domain.client.dto.ProgramDetailAboutRecordDto;
import com.gaenari.backend.domain.program.dto.ProgramDetailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "program-service")
public interface ProgramServiceClient {

    @GetMapping("/program/feign/{programId}")
    ProgramDetailAboutRecordDto getProgramDetailById(@PathVariable("programId") Long programId);

    @GetMapping("/program/feign/use/{programId}")
    Integer updateProgramUsageCount(@PathVariable("programId") Long programId);

}
