package com.gaenari.backend.domain.client.program;

import com.gaenari.backend.domain.client.program.dto.ProgramDetailAboutRecordDto;
import com.gaenari.backend.global.format.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "program-service", url = "${feign.program-service.url}")
public interface ProgramServiceClient {

    @GetMapping("/program/feign/{programId}")
    ResponseEntity<GenericResponse<ProgramDetailAboutRecordDto>> getProgramDetailById(@PathVariable("programId") Long programId);

    @PutMapping("/program/feign/use/{programId}")
    ResponseEntity<GenericResponse<Integer>> updateProgramUsageCount(@PathVariable("programId") Long programId);

}
