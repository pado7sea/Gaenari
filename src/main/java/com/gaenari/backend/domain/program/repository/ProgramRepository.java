package com.gaenari.backend.domain.program.repository;

import com.gaenari.backend.domain.program.entity.Program;
import com.gaenari.backend.domain.program.repository.custom.ProgramRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long>, ProgramRepositoryCustom {
}
