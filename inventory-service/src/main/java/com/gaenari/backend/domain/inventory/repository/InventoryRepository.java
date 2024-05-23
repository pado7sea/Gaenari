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
    void deleteByAccountId(String accountId);
    @Query("SELECT DISTINCT inv FROM Inventory inv JOIN FETCH inv.item item WHERE inv.accountId = :accountId")
    List<Inventory> findByAccountId(@Param("accountId") String accountId);
    List<Inventory> findByAccountIdAndIsEquip(String accountId, Boolean isEquip);
}
