//package com.gaenari.backend.domain.messagequeue;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class KafkaConsumer {
//
//    @KafkaListener(topics = "exercise-completed-topic", groupId = "exercise-group")
//    public void listenExerciseCompleted(ExerciseCompletedEvent event) {
//        // 받은 이벤트 처리
//        System.out.println("Received exercise completed event: " + event);
//
//        // 예: 사용자에게 트로피나 미션 달성 알림을 보냄
//        checkAndAwardTrophies(event.getMemberId());
//    }
//
//    private void checkAndAwardTrophies(String memberId) {
//        // 트로피 또는 미션 달성 로직
//        System.out.println("Checking and awarding trophies to user " + memberId);
//    }
//
//}
