package com.gaenari.backend.domain.record.repository;

import com.gaenari.backend.domain.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    /**
     * 특정 회원의 모든 운동 기록을 조회합니다.
     *
     * @param memberId 회원의 식별자입니다.
     * @return 회원의 모든 운동 기록 목록을 반환합니다.
     */
    List<Record> findAllByMemberId(String memberId);

    /**
     * 특정 기간 동안 특정 회원의 운동 기록을 조회합니다.
     *
     * @param memberId 회원의 식별자입니다.
     * @param startDateTime 조회할 시작 일시입니다.
     * @param endDateTime 조회할 종료 일시입니다.
     * @return 지정된 기간 동안의 회원의 운동 기록 목록을 반환합니다.
     */
    List<Record> findByMemberIdAndDateBetween(String memberId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    /**
     * 특정 프로그램 ID에 해당하는 운동 기록을 날짜 내림차순으로 조회합니다.
     *
     * @param programId 프로그램의 식별자입니다.
     * @return 해당 프로그램에 대한 운동 기록 목록을 반환합니다.
     */
    List<Record> findByProgramIdOrderByDateDesc(Long programId);

    /**
     * 특정 회원의 특정 운동 기록을 조회합니다.
     *
     * @param memberId 회원의 식별자입니다.
     * @param recordId 운동 기록의 식별자입니다.
     * @return 지정된 회원의 특정 운동 기록을 반환합니다.
     */
    Record findByMemberIdAndId(String memberId, Long recordId);

}
