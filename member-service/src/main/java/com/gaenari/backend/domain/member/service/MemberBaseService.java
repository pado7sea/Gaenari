package com.gaenari.backend.domain.member.service;

import com.gaenari.backend.domain.client.inventory.InventoryServiceClient;
import com.gaenari.backend.global.exception.member.ConnectFeignFailException;
import com.gaenari.backend.global.format.response.GenericResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public abstract class MemberBaseService {
    protected final InventoryServiceClient inventoryServiceClient;
    protected void feignCreateNormalItems(String accountId){
        GenericResponse<?> createItemRes = inventoryServiceClient.createNormalItems(accountId).getBody();
    }

    protected void feignDeleteItems(String accountId){
        GenericResponse<?> deleteItemRes = inventoryServiceClient.deleteItems(accountId).getBody();
    }




}
