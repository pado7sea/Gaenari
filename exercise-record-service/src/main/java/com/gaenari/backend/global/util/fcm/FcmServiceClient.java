package com.gaenari.backend.global.util.fcm;

import com.gaenari.backend.global.format.response.GenericResponse;
import com.gaenari.backend.global.util.fcm.dto.FcmMessageRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "fcm-service", url = "${feign.url.fcm-service}")
public interface FcmServiceClient {

  /**
   * fcm 알림 전송
   * @param requestDto - FcmMessageRequestDto
   * @return String
   */
  @PostMapping("/fcm/feign/send")
  ResponseEntity<GenericResponse<String>> sendNotice(@RequestBody FcmMessageRequestDto requestDto);
}
