package com.gaenari.backend.domain.inventory.service;

import com.gaenari.backend.domain.client.MemberServiceClient;
import com.gaenari.backend.domain.inventory.dto.responseDto.*;
import com.gaenari.backend.domain.inventory.entity.Inventory;
import com.gaenari.backend.domain.inventory.repository.InventoryRepository;
import com.gaenari.backend.domain.item.dto.responseDto.Items;
import com.gaenari.backend.domain.item.dto.responseDto.Pets;
import com.gaenari.backend.domain.item.entity.Category;
import com.gaenari.backend.domain.item.entity.Item;
import com.gaenari.backend.domain.item.repository.ItemRepository;
import com.gaenari.backend.global.exception.inventory.ChangeItemFailException;
import com.gaenari.backend.global.exception.inventory.ConnectFeignFailException;
import com.gaenari.backend.global.exception.inventory.NotEnoughCoinException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    private final ItemRepository itemRepository;
    private final InventoryRepository inventoryRepository;
    private final MemberServiceClient memberServiceClient;

    @Override // 회원가입시 기본 아이템 생성
    public void createNormalItems(String memberEmail) {
        // 1번 세트 아이템 다가지고 오기
        List<Item> itemList = itemRepository.findBySetId(1);

        for(Item item : itemList){
            Inventory inventory = Inventory.builder()
                    .memberEmail(memberEmail)
                    .item(item)
                    .isEquip(true)
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override // 회원탈퇴시 아이템 삭제
    public void deleteItems(String memberEmail) {
        inventoryRepository.deleteByMemberEmail(memberEmail);
    }

    @Override // 나의 보관함 보유 아이템 개수
    public List<MySetsCnt> getSets(String memberEmail) {
        // 세트 개수 가져오기
        int setCnt = itemRepository.countUniqueSetIds();
        List<MySetsCnt> mySetsCntList = new ArrayList<>();
        // 세트 개수만큼 dto 넣어놓기
        for(int i=0; i<setCnt; i++){
            MySetsCnt mySetsCnt = MySetsCnt.builder()
                    .setId(i+1)
                    .itemCnt(0)
                    .build();
            mySetsCntList.add(mySetsCnt);
        }
        // 회원이 가지고 있는 아이템들 가져오기
        List<Inventory> inventoryList = inventoryRepository.findByMemberEmail(memberEmail);
        // 가지고 있는 아이템들 확인하면서 해당되는 세트의 카운트 증가시키기
        for(Inventory inventory : inventoryList){
            int setNum = inventory.getItem().getSetId();       // 세트 넘버 가져와
            MySetsCnt mySetsCnt = mySetsCntList.get(setNum-1); // mySetsCntList의 세트넘버-1번째 객체를 가져와
            mySetsCnt.setItemCnt(mySetsCnt.getItemCnt() + 1);  // 개수를 하나 증가시켜
        }

        return mySetsCntList;
    }

    @Override // 나의 보관함 아이템 조회
    @Transactional(readOnly = true)
    public MyInventoryItems getMyItems(String memberEmail, int setId) {

        // 회원이 가지고 있는 아이템들 가져오기
        List<Inventory> inventoryList = inventoryRepository.findByMemberEmail(memberEmail);
        int i = setId;
        // 한 세트내의 모든 아이템들을 담을 그릇
        List<Items> responseItems = new ArrayList<>();

        List<Item> itemList = itemRepository.findBySetId(i);
        // 세트아이디, 아이템 보유개수 파악
        int itemCnt = 0;
        // 한 세트내의 아이템 반복문
        for(Item item : itemList){
            // 소유여부, 장착여부를 조회
            boolean isHave = false;
            boolean isEquip = false;
            // 회원이 가지고 있는 아이템들과 조건에 맞는지 비교
            for(Inventory inventory : inventoryList) {
                if(inventory.getItem().getId() == item.getId()) {
                    isHave = true;
                    itemCnt++;
                    isEquip = inventory.getIsEquip();

                    Items getItems = Items.builder()
                            .id(item.getId())
                            .category(item.getCategory())
                            .isEquip(isEquip)
                            .isHave(isHave)
                            .build();
                    responseItems.add(getItems);
                    break; // 해당 아이템을 찾았으면 루프 종료
                }
            }
            // 가지고 있는 아이템이였으면 다음 for문으로 넘어가기
            if(isHave){
                continue;
            }
            Items getItems = Items.builder()
                    .id(item.getId())
                    .category(item.getCategory())
                    .isEquip(isEquip)
                    .isHave(isHave)
                    .build();
            responseItems.add(getItems);
        }

        // myInventoryItemsList에 MyInventoryItems 객체 넣기
        MyInventoryItems myInventoryItems = MyInventoryItems.builder()
                .setId(i)
                .itemCnt(itemCnt)
                .items(responseItems)
                .build();


        return myInventoryItems;
    }

    @Override // 나의 보관함 펫 조회
    public List<MyInventoryPets> getMyPets(String memberEmail) {
        // 회원이 가지고 있는 펫 조회
        List<Pets> res = memberServiceClient.getPets(memberEmail);
        if(res == null){
            throw new ConnectFeignFailException();
        }
        List<MyInventoryPets> myInventoryItemsList = new ArrayList<>();
        for(int i=1; i<=10; i++){
            Pets resPets = null;

            Pets havePet = null;
            Boolean isHave = false;
            for(Pets pets : res){
                if(pets.getId() == i){
                    isHave = true;
                    havePet = pets;
                    break; // 찾았다면 탈출
                }
            }
            // 해당 종의 펫을 가지고 있다면
            if(isHave){
                resPets = Pets.builder()
                        .id((long)i)
                        .name(havePet.getName())
                        .affection(havePet.getAffection())
                        .tier(havePet.getTier())
                        .isPartner(havePet.getIsPartner())
                        .price(havePet.getPrice())
                        .build();
            }else{
                int dogPrice = memberServiceClient.getDogPrice(i);
                resPets = Pets.builder()
                        .id((long)i)
                        .name(null)
                        .affection((long)-1)
                        .tier(null)
                        .isPartner(false)
                        .price(dogPrice)
                        .build();
            }

            MyInventoryPets myInventoryPets = MyInventoryPets.builder()
                    .isHave(isHave)
                    .pets(resPets)
                    .build();
            myInventoryItemsList.add(myInventoryPets);
        }
        return myInventoryItemsList;

    }

    @Override // 카테고리 적용 아이템 조회
    public Long getEquipCategory(String memberEmail, Category category) {
        // 회원이 장착하고 있는 아이템 모두 조회
        List<Inventory> inventoryList = inventoryRepository.findByMemberEmailAndIsEquip(memberEmail, true);
        // 카테고리에 해당하는 아이템 id 가져오기
        Long equipItemId = null;
        for(Inventory inventory : inventoryList){
            if(inventory.getItem().getCategory().equals(category)){
                equipItemId = inventory.getItem().getId();
            }
        }
        return equipItemId;
    }

    @Override // 회원 적용 아이템(펫, 아이템) 조회
    public MyEquipItems getEquipItems(String memberEmail) {
        // 회원이 가지고 있는 펫 조회
        List<Pets> res = memberServiceClient.getPets(memberEmail);
        if(res == null){
            throw new ConnectFeignFailException();
        }
        // 회원 파트너 반려견 조회
        FriendPet friendPet = null;
        for(Pets pet : res){
            if(pet.getIsPartner()){
                friendPet = FriendPet.builder()
                        .id(pet.getId())
                        .name(pet.getName())
                        .affection(pet.getAffection())
                        .tier(pet.getTier())
                        .build();
                break;
            }
        }
        // 적용된 아이템 조회
        List<Items> itemsList = new ArrayList<>();
        List<Inventory> inventoryList = inventoryRepository.findByMemberEmail(memberEmail);
        for(Inventory inventory : inventoryList){
            if(inventory.getIsEquip()){
                Items items = Items.builder()
                        .id(inventory.getItem().getId())
                        .category(inventory.getItem().getCategory())
                        .isEquip(true)
                        .isHave(true)
                        .build();
                itemsList.add(items);
            }
        }

        // MyEquipItems Dto로 변환
        MyEquipItems myEquipItems = MyEquipItems.builder()
                .pet(friendPet)
                .items(itemsList)
                .build();

        return myEquipItems;
    }

    @Override // 회원 이메일 조회
    public String getMateEmail(Long mateId) {
        String mateEmail = memberServiceClient.getMateEmail(mateId);
        if(mateEmail == null){
            throw new ConnectFeignFailException();
        }
        return mateEmail;
    }

    @Override // 아이템 적용
    public void updateItems(String memberEmail, Category category, Long itemId) {
        // 현재 가지고있는 아이템 조회
        List<Inventory> inventoryList = inventoryRepository.findByMemberEmail(memberEmail);
        boolean cancel = true;
        boolean equip = true;
        for(Inventory inventory : inventoryList){
            // 카테고리가 같다면
            if(inventory.getItem().getCategory().equals(category)){
                // 적용된 아이템이라면
                if(cancel && inventory.getIsEquip()){
                    cancel = false;
                    // 적용된 아이템은 적용 해제
                    Inventory clearItem = Inventory.builder()
                            .Id(inventory.getId())
                            .memberEmail(inventory.getMemberEmail())
                            .item(inventory.getItem())
                            .isEquip(false)
                            .build();
                    inventoryRepository.save(clearItem);
                }
                // 새롭게 적용할 아이템이라면
                if(equip && inventory.getItem().getId().equals(itemId)){
                    Inventory equipItem = Inventory.builder()
                            .Id(inventory.getId())
                            .memberEmail(inventory.getMemberEmail())
                            .item(inventory.getItem())
                            .isEquip(true)
                            .build();
                    inventoryRepository.save(equipItem);
                    equip = false;
                }
            }
        }
        // cancel과 equip이 false라면 잘 변경됨
        if(cancel || equip){
            throw new ChangeItemFailException();
        }

    }

    @Override // 아이템 랜덤 구매
    public Items randomItem(String memberEmail) {
        // 기존 코인 조회후, 아이템 구매가 가능한지 확인
        int haveCoin = memberServiceClient.getMemberCoin(memberEmail);
        if(haveCoin < 1000){
            throw new NotEnoughCoinException();
        }
        // 코인 감소시키기
        MemberCoin memberCoin = MemberCoin.builder()
                .memberEmail(memberEmail)
                .coin(1000)
                .isIncreased(false)
                .build();
        ResponseEntity<?> res = memberServiceClient.updateCoin(memberCoin);
        int code = res.getStatusCode().value();
        if(code != 200){
            throw new ConnectFeignFailException();
        }
        // 모든 아이템 다 가져오기
        List<Item> itemList = itemRepository.findAll();
        // 랜덤으로 아이템 선택
        Random random = new Random();
        int randomIndex = random.nextInt(itemList.size());
        Item selectItem = itemList.get(randomIndex);
        // 현재 가지고있는 아이템 조회
        List<Inventory> inventoryList = inventoryRepository.findByMemberEmail(memberEmail);
        // 해당 아이템 소유/장착 여부 확인
        Boolean checkHave = false;
        Boolean checkEquip = false;
        Inventory addInventory = null;
        for(Inventory inventory : inventoryList){
            // 가지고 있는 아이템 중에 해당 아이템이 있다면
            if(inventory.getItem().getId().equals(selectItem.getId())){
                // 장착하고 있다면
                if(inventory.getIsEquip()){
                    checkHave = true;
                    checkEquip = true;
                    break;
                }
                checkHave = true;
                break;
            }
        }
        // 인벤토리에 저장
        addInventory = Inventory.builder()
                .memberEmail(memberEmail)
                .item(selectItem)
                .isEquip(false)
                .build();
        inventoryRepository.save(addInventory);

        Items items = Items.builder()
                .id(selectItem.getId())
                .category(selectItem.getCategory())
                .isEquip(checkEquip)
                .isHave(checkHave)
                .build();

        return items;
    }
}
