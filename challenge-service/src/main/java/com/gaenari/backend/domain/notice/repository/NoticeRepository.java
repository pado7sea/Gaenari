package com.gaenari.backend.domain.notice.repository;

import com.gaenari.backend.domain.notice.entity.Notice;
import com.gaenari.backend.domain.notice.repository.custom.NoticeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {
}
