package com.gaenari.backend.domain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventory-service", url = "${feign.inventory-service.url}")
public interface InventoryServiceClient {
    @PostMapping("/inventory/items/{memberEmail}") // 회원가입시 기본 아이템 생성
    ResponseEntity<?> createNormalItems(@PathVariable String memberEmail);

    @DeleteMapping("/inventory/items/{memberEmail}") // 회원탈퇴시 아이템 삭제
    ResponseEntity<?> deleteItems(@PathVariable String memberEmail);
}
