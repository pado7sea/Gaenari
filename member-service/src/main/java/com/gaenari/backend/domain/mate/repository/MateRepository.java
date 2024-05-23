package com.gaenari.backend.domain.mate.repository;

import com.gaenari.backend.domain.mate.entity.Mate;
import com.gaenari.backend.domain.mate.entity.State;
import com.gaenari.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateRepository extends JpaRepository<Mate, Long> {
    Mate findByFriend1AndFriend2(Member friendId1, Member friendId2);
    Mate findByFriend2AndFriend1(Member friendId1, Member friendId2);
    List<Mate> findByFriend1AndState(Member memberId, State state);
    List<Mate> findByFriend2AndState(Member memberId, State state);

    @Query("SELECT m FROM Mate m JOIN FETCH m.friend1 friend1 JOIN FETCH m.friend2 friend2 WHERE friend1.Id = :memberId")
    List<Mate> findByFriend1(@Param("memberId") Long member);

    @Query("SELECT m FROM Mate m JOIN FETCH m.friend1 friend1 JOIN FETCH m.friend2 friend2 WHERE friend2.Id = :memberId")
    List<Mate> findByFriend2(@Param("memberId") Long member);
}
