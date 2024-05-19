package com.gaenari.backend.domain.mypet.service;

import com.gaenari.backend.domain.client.challenge.ChallengeServiceClient;
import com.gaenari.backend.global.exception.member.ExistRewardException;
import com.gaenari.backend.global.format.response.GenericResponse;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public abstract class MyPetBaseService {
    protected final ChallengeServiceClient challengeServiceClient;
    protected boolean feignIsGetReward(String accountId){
        GenericResponse<?> getRewardRes = challengeServiceClient.isGetReward(accountId).getBody();
        if(!getRewardRes.getStatus().equals("FALLBACK")){
            log.error("FALLBACK response: {}", accountId);
        }
        return (boolean) getRewardRes.getData();
    }


}
