package com.gaenari.backend.domain.memberChallenge.repository;

import com.gaenari.backend.domain.memberChallenge.entity.MemberChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberChallengeRepository extends JpaRepository<MemberChallenge, Long> {

    // 회원 아이디로 도전과제 리스트 조회
    List<MemberChallenge> findByMemberId(String memberId);

    // 회원 아이디로 해당 회원이 달성한 도전 과제 아이디만 조회
    List<MemberChallenge> findByMemberIdAndIsAchievedIsTrue(String memberId);

    // 회원 아이디와 도전 과제 아이디로 해당 회원의 도전 과제 정보를 조회
    MemberChallenge findByMemberIdAndChallengeId(String memberId, Integer challengeId);

    // 업데이트된 회원 도전과제 정보를 저장
    MemberChallenge save(MemberChallenge memberChallenge);

}
