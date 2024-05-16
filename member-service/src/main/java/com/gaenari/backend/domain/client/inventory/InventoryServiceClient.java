package com.gaenari.backend.domain.client.inventory;

import com.gaenari.backend.global.format.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventory-service", url = "${feign.inventory-service.url}")
public interface InventoryServiceClient {
    @PostMapping("/inventory/items/{accountId}") // 회원가입시 기본 아이템 생성
    ResponseEntity<GenericResponse<?>> createNormalItems(@PathVariable String accountId);

    @DeleteMapping("/inventory/items/{accountId}") // 회원탈퇴시 아이템 삭제
    ResponseEntity<GenericResponse<?>> deleteItems(@PathVariable String accountId);
}
