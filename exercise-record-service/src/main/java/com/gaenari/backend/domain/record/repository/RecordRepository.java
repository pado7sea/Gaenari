package com.gaenari.backend.domain.record.repository;

import com.gaenari.backend.domain.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByMemberId(String memberId);

    List<Record> findByMemberIdAndDateBetween(String memberId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Record> findByProgramIdOrderByDateDesc(Long programId);

    Record findByMemberIdAndId(String memberId, Long recordId);

}
