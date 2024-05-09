package com.gaenari.backend.domain.client.member;

import com.gaenari.backend.domain.client.member.dto.HeartChangeDto;
import com.gaenari.backend.domain.client.member.dto.MemberCoinDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "member-service", url = "${feign.member-service.url}")
public interface MemberServiceClient {

    // 회원 코인 증가
    @PostMapping("/member/coin")
    ResponseEntity<?> updateCoin(@RequestBody MemberCoinDto memberCoinDto);

    // 파트너 펫 애정도 증가
    @PostMapping("/pet/heart/change")
    ResponseEntity<?> updateHeart(@RequestBody HeartChangeDto heartChangeDto);

}
