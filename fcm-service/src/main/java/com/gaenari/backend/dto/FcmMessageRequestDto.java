package com.gaenari.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "FCM 메세지 요청 DTO")
@Data
@Builder
public class FcmMessageRequestDto {

  @Schema(description = "수신자 아이디")
  private String memberId;

  @Schema(description = "FCM 알림 제목")
  private String title;

  @Schema(description = "FCM 알림 내용")
  private String content;
}