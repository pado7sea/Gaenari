package com.gaenari.backend.domain.inventory.repository;

import com.gaenari.backend.domain.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Transactional
    void deleteByMemberEmail(String memberEmail);
    @Query("SELECT DISTINCT inv FROM Inventory inv JOIN FETCH inv.item item WHERE inv.memberEmail = :memberEmail")
    List<Inventory> findByMemberEmail(@Param("memberEmail") String memberEmail);
    List<Inventory> findByMemberEmailAndIsEquip(String memberEmail, Boolean isEquip);
}
