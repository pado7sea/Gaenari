package com.gaenari.backend.domain.client.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

//    // TODO : 최근 운동기록 날짜 기준으로 일정 기간 지나면 애정도 떨어뜨리는 스케줄 작성. 애정도 감소 요청 보내기
//    // 파트너 펫 애정도 감소
//    @PostMapping("/pet/partner/heart/decrease")
//    Boolean decreaseHeart(@PathVariable(name = "memberId") String memberId);

    // 회원 체중 조회
    @GetMapping("/member/weight/{memberEmail}")
    ResponseEntity<?> getWeight(@PathVariable(name = "memberEmail") String memberId);

}