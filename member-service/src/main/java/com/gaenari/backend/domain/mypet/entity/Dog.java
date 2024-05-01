package com.gaenari.backend.domain.mypet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dog")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dog_id")
    private Long Id;

    @Column(name = "dog_breed", unique = true)
    @NotNull
    private String breed;

    @Column(name = "dog_price")
    @NotNull
    private int price;

    @OneToMany(mappedBy = "dog", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<MyPet> myPetList = new ArrayList<>();
}
