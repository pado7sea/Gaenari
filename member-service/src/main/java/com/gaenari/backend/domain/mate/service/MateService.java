package com.gaenari.backend.domain.mate.service;

import com.gaenari.backend.domain.mate.dto.requestDto.MateCheck;
import com.gaenari.backend.domain.mate.dto.responseDto.ApplyMate;
import com.gaenari.backend.domain.mate.dto.responseDto.Mates;
import com.gaenari.backend.domain.mate.dto.responseDto.SearchMates;

import java.util.List;

public interface MateService {
    void addMate(String accountId, Long mateId); // 친구신청
    List<ApplyMate> getSentMate(Long memberId, String type); // 친구신청 발신/수신 목록 조회
    void checkMate(MateCheck mateCheck); // 친구신청 수락/거부
    List<ApplyMate> getMates(Long memberId); // 친구목록 조회
    void deleteMate(Long memberId, Long friendId); // 친구삭제
    List<SearchMates> getMembers(Long memId, String nickName); // 친구검색

}
