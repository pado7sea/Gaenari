package com.gaenari.backend.domain.recordDetail.controller;

import com.gaenari.backend.domain.recordDetail.dto.RecordDetailDto;
import com.gaenari.backend.domain.recordDetail.service.RecordDetailService;
import com.gaenari.backend.global.format.code.ResponseCode;
import com.gaenari.backend.global.format.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Record Detail Controller", description = "Record Detail Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordDetailController {

    private final ApiResponse response;
    private final RecordDetailService recordDetailService;

    @Operation(summary = "기록 상세 조회", description = "기록 상세 조회")
    @GetMapping("/{recordId}")
    public ResponseEntity<?> getDetailRecord(@Parameter(hidden = true) @RequestHeader("User-Info") String memberId,
                                             @Parameter(name = "운동 기록 ID") @PathVariable(name = "recordId") Long recordId) {
        RecordDetailDto recordDtos = recordDetailService.getExerciseRecordDetail(memberId, recordId);

        return response.success(ResponseCode.RECORD_DETAIL_FETCHED, recordDtos);
    }

}
