package com.gaenari.backend.domain.client.member;

import com.gaenari.backend.domain.client.member.dto.HeartChangeDto;
import com.gaenari.backend.global.format.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "member-service", url = "${feign.url.member-service}")
public interface MemberServiceClient {

    // 회원 체중 조회
    @GetMapping("/member/weight/{accountId}")
    ResponseEntity<GenericResponse<Integer>> getWeight(@PathVariable(name = "accountId") String accountId);

    // 파트너 펫 애정도 업데이트
    @PutMapping("/pet/heart")
    ResponseEntity<GenericResponse<?>> updateHeart(@RequestBody HeartChangeDto heartChangeDto);


}