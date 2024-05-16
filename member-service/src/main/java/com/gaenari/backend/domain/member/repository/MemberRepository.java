package com.gaenari.backend.domain.member.repository;

import com.gaenari.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByAccountId(String accountId);
    Optional<Member> findById(Long memberId);
    @Query("SELECT m FROM Member m WHERE m.nickname LIKE %:nickname%")
    List<Member> findByNicknameContaining(@Param("nickname") String nickname);
    Member findByNickname(String nickname);
    Member findByDevice(String device);
}
