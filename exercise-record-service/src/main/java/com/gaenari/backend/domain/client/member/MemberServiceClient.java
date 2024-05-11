package com.gaenari.backend.domain.client.member;

import com.gaenari.backend.domain.client.member.dto.HeartChangeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "member-service", url = "${feign.member-service.url}")
public interface MemberServiceClient {
    
    // 회원 체중 조회
    @GetMapping("/member/weight/{memberEmail}")
    ResponseEntity<?> getWeight(@PathVariable(name = "memberEmail") String memberId);

    // 파트너 펫 애정도 업데이트
    @PutMapping("/pet/heart")
    ResponseEntity<?> updateHeart(@RequestBody HeartChangeDto heartChangeDto);


}