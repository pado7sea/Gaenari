package com.gaenari.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name="Fcm 토큰 등록 RequestDto")
public class FcmRegisterRequestDto {

  @Schema(name="사용자 아이디")
  private String memberId;

  @Schema(name="Fcm 토큰")
  private String fcmToken;
}
