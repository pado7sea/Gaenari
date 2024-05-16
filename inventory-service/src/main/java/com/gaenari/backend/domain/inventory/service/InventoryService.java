package com.gaenari.backend.domain.inventory.service;

import com.gaenari.backend.domain.inventory.dto.responseDto.MyEquipItems;
import com.gaenari.backend.domain.inventory.dto.responseDto.MyInventoryItems;
import com.gaenari.backend.domain.inventory.dto.responseDto.MyInventoryPets;
import com.gaenari.backend.domain.inventory.dto.responseDto.MySetsCnt;
import com.gaenari.backend.domain.item.dto.responseDto.Items;
import com.gaenari.backend.domain.item.entity.Category;

import java.util.List;

public interface InventoryService {
    void createNormalItems(String accountId); // 기본 아이템 생성
    void deleteItems(String accountId); // 회원 아이템 전체 삭제
    List<MySetsCnt> getSets(String accountId); // 회원 인벤토리 아이템 개수 조회
    MyInventoryItems getMyItems(String accountId, int setId); // 회원 인벤토리(아이템) 조회
    List<MyInventoryPets> getMyPets(String accountId); // 회원 인벤토리(펫) 조회
    Long getEquipCategory(String accountId, Category category); // 카테고리 적용 아이템 조회
    MyEquipItems getEquipItems(String accountId); // 회원 적용 아이템(펫, 아이템) 조회
    String getMateEmail(Long mateId); // 회원 이메일 조회
    void updateItems(String accountId, Category category, Long itemId); // 아이템 적용
    Items randomItem(String accountId); // 아이템 랜덤 구매

}
