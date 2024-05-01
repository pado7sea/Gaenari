package com.gaenari.backend.domain.program;

import com.gaenari.backend.domain.program.dto.ProgramDetailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "program/msa")
public interface ProgramServiceClient {

    @GetMapping("/{programId}")
    ProgramDetailDto getProgramInfo(@PathVariable("programId") Long programId);

}
