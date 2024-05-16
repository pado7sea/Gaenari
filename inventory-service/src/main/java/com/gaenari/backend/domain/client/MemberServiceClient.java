package com.gaenari.backend.domain.client;

import com.gaenari.backend.domain.client.dto.MemberCoin;
import com.gaenari.backend.domain.item.dto.responseDto.Pets;
import com.gaenari.backend.global.format.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "member-service", url = "${feign.member-service.url}")
public interface MemberServiceClient {
    @GetMapping("/pet/{accountId}") // 회원이 가지고 있는 펫 조회
    ResponseEntity<GenericResponse<List<Pets>>> getPets(@PathVariable String accountId);

    @GetMapping("/pet/dog/{dogId}") // 강아지 가격 조회
    ResponseEntity<GenericResponse<?>> getDogPrice(@PathVariable int dogId);

    @GetMapping("/mate/email/{mateId}") // 친구 이메일 조회
    ResponseEntity<GenericResponse<?>> getMateEmail(@PathVariable Long mateId);

    @PutMapping("/coin") // 회원 코인 증/감
    ResponseEntity<GenericResponse<?>> updateCoin(@RequestBody MemberCoin memberCoin);

    @GetMapping("/coin/{accountId}") // 회원보유코인 조회
    ResponseEntity<GenericResponse<?>> getMemberCoin(@PathVariable String accountId);
}
