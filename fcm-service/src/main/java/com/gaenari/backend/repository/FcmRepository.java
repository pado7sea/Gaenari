package com.gaenari.backend.repository;

import com.gaenari.backend.entity.Fcm;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmRepository extends JpaRepository<Fcm, Long> {

  Optional<Fcm> findByMemberId(String memberId);

  Boolean existsByFcmToken(String token);
}
