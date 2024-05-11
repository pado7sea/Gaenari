package com.gaenari.backend.domain.item.entity;

import com.gaenari.backend.domain.inventory.entity.Inventory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long Id;

    @Column(name = "item_set_id")
    @NotNull
    private int setId;

    @Column(name = "item_category")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Inventory> inventoryList = new ArrayList<>();
}
