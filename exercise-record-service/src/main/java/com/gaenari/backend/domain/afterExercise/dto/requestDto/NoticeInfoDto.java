package com.gaenari.backend.domain.afterExercise.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "운동 시작 알림 전송을 위한 운동 정보 Dto")
public class NoticeInfoDto {
  
  @Schema(description = "프로그램 제목")
  private String programTitle;
  @Schema(description = "프로그램 타입")
  private String programType;
  @Schema(description = "운동 일자")
  private LocalDateTime exerciseDateTime;
}
