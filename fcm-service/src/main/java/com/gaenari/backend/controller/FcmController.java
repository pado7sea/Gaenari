package com.gaenari.backend.controller;

import com.gaenari.backend.dto.FcmMessageRequestDto;
import com.gaenari.backend.dto.FcmRegisterRequestDto;
import com.gaenari.backend.service.FcmService;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Fcm Feign Controller", description = "Fcm Feign Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FcmController {

  private final FcmService fcmService;

  @Operation(summary = "[Feign] Fcm 토큰 저장", description = "Fcm 토큰 정보 저장")
  @PostMapping("/save")
  @ApiResponse(responseCode = "200", description = "[Feign] Fcm 토큰 저장 성공")
  public ResponseEntity<?> saveToken(@RequestBody FcmRegisterRequestDto requestDto){
    fcmService.register(requestDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "[Feign] 개인 메세지 전송", description = "Fcm을 활용한 개인 메세지 전송")
  @PostMapping("/send")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "[Feign] 메세지 전송 성공", content = @Content(schema = @Schema(implementation = String.class)))
  })
  public ResponseEntity<?> sendMessage(@RequestBody FcmMessageRequestDto requestDto) throws FirebaseMessagingException{
    fcmService.sendNotice(requestDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
