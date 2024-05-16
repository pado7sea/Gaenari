package com.gaenari.backend.service;

import com.gaenari.backend.dto.FcmMessageRequestDto;
import com.gaenari.backend.dto.FcmRegisterRequestDto;
import com.gaenari.backend.entity.Fcm;
import com.gaenari.backend.repository.FcmRepository;
import com.gaenari.backend.util.exception.fcm.AlreadyRegisterTokenException;
import com.gaenari.backend.util.exception.fcm.FcmTokenNotFoundException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

  private final FcmRepository fcmRepository;
  private final FirebaseMessaging firebaseMessaging;

  /**
   * FCM 토큰을 유저에 매칭해서 DB에 저장
   *
   * @param requestDto FcmRegisterRequestDto
   * @return Boolean
   */
  public void register(FcmRegisterRequestDto requestDto) {
    if (fcmRepository.existsByFcmToken(requestDto.getFcmToken())) {
      throw new AlreadyRegisterTokenException();
    }

    fcmRepository.save(Fcm.builder()
        .memberId(requestDto.getMemberId())
        .fcmToken(requestDto.getFcmToken())
        .build());
  }

  /**
   * FcmMessage 전송
   *
   * @param fcmMessage 메세지 dto
   * @throws FirebaseMessagingException 예외
   */
  public void sendNotice(FcmMessageRequestDto fcmMessage) throws FirebaseMessagingException {
    Optional<Fcm> fcm = fcmRepository.findByMemberId(fcmMessage.getMemberId());

    if (fcm.isEmpty()) {
      throw new FcmTokenNotFoundException();
    }

    Notification notification =
        Notification.builder()
            .setTitle(fcmMessage.getTitle())
            .setBody(fcmMessage.getContent())
            .build();

    Message message = Message.builder()
        .setToken(fcm.get().getFcmToken())
        .setNotification(notification)
        .build();

    firebaseMessaging.send(message);
  }
}
