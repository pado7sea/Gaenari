package com.gaenari.backend.domain.item.repository;

import com.gaenari.backend.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findBySetId(int setId);
    @Query("SELECT COUNT(DISTINCT i.setId) FROM Item i")
    int countUniqueSetIds();
}
