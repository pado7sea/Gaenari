package com.gaenari.backend.domain.mypet.service;

import com.gaenari.backend.domain.mypet.dto.requestDto.Adopt;
import com.gaenari.backend.domain.mypet.dto.requestDto.HeartChange;
import com.gaenari.backend.domain.mypet.dto.requestDto.IncreaseAffection;
import com.gaenari.backend.domain.mypet.dto.responseDto.FriendPetDetail;
import com.gaenari.backend.domain.mypet.dto.responseDto.Pets;

import java.util.List;

public interface MyPetService {
    void adopt(String memberEmail, Adopt adopt); // 반려견 입양
    void changePartner(String memberEmail, Long dogId); // 파트너 반려견 변경
    FriendPetDetail getPartner(String memberEmail); // 파트너 반려견 조회
    void increaseAffection(String memberEmail, IncreaseAffection affection); // 반려견 애정도 증가
    void changeHeart(HeartChange heartChange); // 반려견 애정도 증/감
    List<Pets> getPets(String memberEmail); // 반려견 전체 조회
    int getDogPrice(int dogId); // 강아지 가격 조회
}
