package com.gaenari.backend.domain.recordDetail.controller;

import com.gaenari.backend.domain.recordDetail.dto.RecordDetailDto;
import com.gaenari.backend.domain.recordDetail.service.RecordDetailService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Record Detail Controller", description = "Record Detail Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordDetailController {

    private final ApiResponse response;
    private final RecordDetailService recordDetailService;

    @Operation(summary = "기록 상세 조회", description = "기록 상세 조회")
    @GetMapping("/{recordId}")
    public ResponseEntity<?> getDetailRecord(@PathVariable(name = "recordId") Long exerciseId) {
        Long memberId = 1L;
        RecordDetailDto recordDtos = recordDetailService.getExerciseRecordDetail(memberId, exerciseId);

        return response.success(ResponseCode.RECORD_DETAIL_FETCHED, recordDtos);
    }

}
