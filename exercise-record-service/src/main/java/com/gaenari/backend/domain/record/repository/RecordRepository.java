package com.gaenari.backend.domain.record.repository;

import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.repository.custom.RecordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long>, RecordRepositoryCustom {
    List<Record> findAllByMemberId(Long memberId);

    List<Record> findByMemberIdAndDateBetween(Long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime);

//    List<Record> findByMemberIdAndDateBetween(Long memberId, LocalDateTime datetime);
}
