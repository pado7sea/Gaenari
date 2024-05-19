package com.gaenari.backend.domain.inventory.service;

import com.gaenari.backend.domain.client.MemberServiceClient;
import com.gaenari.backend.domain.client.dto.MemberCoin;
import com.gaenari.backend.domain.item.dto.responseDto.Pets;
import com.gaenari.backend.global.format.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryBaseService {
    protected final MemberServiceClient memberServiceClient;

    protected List<Pets> feignGetPets(String accountId){
        GenericResponse<List<Pets>> getPetList = memberServiceClient.getPets(accountId).getBody();
        List<Pets> petList = getPetList.getData();
        return petList;
    }
    protected int feignGetDogPrice(int dogId){
        GenericResponse<?> getDogPrice = memberServiceClient.getDogPrice(dogId).getBody();
        int price = (int) getDogPrice.getData();
        return price;
    }
    protected String feignGetMateAccountId(Long memberId){
        GenericResponse<?> getAccountId = memberServiceClient.getMateAccountId(memberId).getBody();
        String accountId = getAccountId.getData().toString();
        return accountId;
    }
    protected void feignUpdateCoin(MemberCoin memberCoin){
        GenericResponse<?> updateCoin = memberServiceClient.updateCoin(memberCoin).getBody();
    }
    protected int feignGetMemberCoin(String accountId){
        GenericResponse<?> getCoin = memberServiceClient.getMemberCoin(accountId).getBody();
        int coin = (int) getCoin.getData();
        return coin;
    }
}
