package com.gaenari.backend.domain.mate.service;

import com.gaenari.backend.domain.mate.dto.requestDto.MateCheck;
import com.gaenari.backend.domain.mate.dto.responseDto.ApplyMate;
import com.gaenari.backend.domain.mate.dto.responseDto.SearchMates;
import com.gaenari.backend.domain.mate.entity.Call;
import com.gaenari.backend.domain.mate.entity.Mate;
import com.gaenari.backend.domain.mate.entity.State;
import com.gaenari.backend.domain.mate.repository.MateRepository;
import com.gaenari.backend.domain.member.entity.Member;
import com.gaenari.backend.domain.member.repository.MemberRepository;
import com.gaenari.backend.domain.mypet.entity.MyPet;
import com.gaenari.backend.domain.mypet.repository.MyPetRepository;
import com.gaenari.backend.global.exception.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.gaenari.backend.domain.mate.entity.State.*;

@Service
@RequiredArgsConstructor
public class MateServiceImpl implements MateService{
    private final MateRepository mateRepository;
    private final MemberRepository memberRepository;
    private final MyPetRepository myPetRepository;

    @Override // 친구신청
    public void addMate(String memberEmail, Long mateId) {
        // 발신 회원
        Member existMember = memberRepository.findByEmail(memberEmail);
        // 수신 회원
        Member receiveMember = memberRepository.findById(mateId).orElseThrow(MemberNotFoundException::new);
        // 본인이 본인에게 친구신청할 수 없음
        if(existMember.getId().equals(mateId)) {
            throw new TomeMateException();
        }

        // 친구였던적이 있는지 조회하기
        Mate mate = null;
        mate = mateRepository.findByFriend1AndFriend2(existMember, receiveMember);
        if(mate == null){
            mate = mateRepository.findByFriend2AndFriend1(existMember, receiveMember);
        }

        Mate saveMate = null;
        // 친구였던적이 없으면
        if(mate == null){
            saveMate = Mate.builder()
                    .friend1(existMember)
                    .friend2(receiveMember)
                    .state(WAITTING)
                    .build();
            mateRepository.save(saveMate);
        }else{
            saveMate = Mate.builder()
                    .Id(mate.getId())
                    .friend1(existMember)
                    .friend2(receiveMember)
                    .state(WAITTING)
                    .build();
            mateRepository.save(saveMate);
        }

    }

    @Override // 친구신청 발신/수신 목록 조회
    public List<ApplyMate> getSentMate(Long memberId, String type) {
        // 멤버 엔티티 조회
        Member me = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        List<Mate> applyMates = null;
        if(type.equals("sent")){
            // 발신 Mate를 조회
            applyMates = mateRepository.findByFriend1AndState(me, WAITTING);
        }else{
            // 수신 Mate를 조회
            applyMates = mateRepository.findByFriend2AndState(me, WAITTING);
        }
        // 친구 신청 목록이 없을때 빈리스트를 반환
        if (applyMates == null || applyMates.isEmpty()) {
            return Collections.emptyList();
        }

        List<ApplyMate> applyMateList = new ArrayList<>();
        // ApplyMate Dto로 변환
        for(Mate mate : applyMates){
            // 친구 정보 조회
            Member friend = null;
            if(type.equals("sent")){
                friend = memberRepository.findById(mate.getFriend2().getId()).orElseThrow(MemberNotFoundException::new);
            }else{
                friend = memberRepository.findById(mate.getFriend1().getId()).orElseThrow(MemberNotFoundException::new);
            }
            // 친구 반려견 정보 조회
            List<MyPet> friendPet = friend.getMyPetList();
            // 파트너 반려견만 조회
            MyPet partnerPet = null;
            for(MyPet myPet : friendPet){
                if(myPet.getIsPartner()){
                    partnerPet = myPet;
                }
            }
            // 파트너 반려견이 없다면 에러
            if(partnerPet == null){
                throw new PartnerPetNotFoundException();
            }
            // ApplyMate Dto에 담기
            ApplyMate applyMate = ApplyMate.builder()
                    .mateId(mate.getId())
                    .memberId(friend.getId())
                    .nickName(friend.getNickname())
                    .petId(partnerPet.getDog().getId())
                    .petName(partnerPet.getName())
                    .petTier(partnerPet.getTier())
                    .build();

            applyMateList.add(applyMate);
        }
        return applyMateList;
    }

    @Override // 친구신청 수락/거부
    public void checkMate(MateCheck mateCheck) {
        Mate getMate = mateRepository.findById(mateCheck.getMateId()).orElseThrow(MateNotFoundException::new);
        State updateState = null;
        if(mateCheck.getIsAccept()){
            // 이미 친구라면 예외처리
            if(getMate.getState() == FRIEND){
                throw new AlreadyMateException();
            }
            // true(친구 수락)
            updateState = FRIEND;
        }else {
            // 친구신청을 한 상태가 아니라면 예외처리
            if(getMate.getState() == NOTFRIEND){
                throw new AlreadyUnMateException();
            }
            // false(친구 거절)
            updateState = NOTFRIEND;
        }
        Mate saveMate = Mate.builder()
                .Id(getMate.getId())
                .friend1(getMate.getFriend1())
                .friend2(getMate.getFriend2())
                .state(updateState)
                .build();
        mateRepository.save(saveMate);
    }

    @Override // 친구목록 조회
    public List<ApplyMate> getMates(Long memberId) {
        // 멤버 엔티티 조회
        Member me = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        List<ApplyMate> matesList = new ArrayList<>();
        // memberId가 friend1인 경우
        for(int i=1; i<=2; i++) {
            List<Mate> mates = new ArrayList<>();
            if(i==1){
                List<Mate> myMateList = mateRepository.findByFriend1(me.getId());
                for(Mate myMate : myMateList){
                    // 친구인 사람들만 담기
                    if(myMate.getState().equals(FRIEND)){
                        mates.add(myMate);
                    }
                }
            }else{
                List<Mate> myMateList = mateRepository.findByFriend2(me.getId());
                for(Mate myMate : myMateList){
                    // 친구인 사람들만 담기
                    if(myMate.getState().equals(FRIEND)){
                        mates.add(myMate);
                    }
                }
            }
            // 친구 목록이 없으면 continue
            if(mates==null){
                continue;
            }
            for(Mate mate : mates) {
                // friendId로 member를 조회
                Member friend;
                if(i==1){
                    friend = memberRepository.findById(mate.getFriend2().getId())
                            .orElseThrow(MemberNotFoundException::new);
                }else {
                    friend = memberRepository.findById(mate.getFriend1().getId())
                            .orElseThrow(MemberNotFoundException::new);
                }
                // 친구의 대표 반려견 조회
                MyPet friendPartnerPet = myPetRepository.findByMemberIdAndIsPartner(friend.getId(), true)
                        .orElseThrow(PartnerPetNotFoundException::new);
                // ApplyMate response 형태로 변환
                ApplyMate resMate = ApplyMate.builder()
                        .mateId(mate.getId())
                        .memberId(friend.getId())
                        .nickName(friend.getNickname())
                        .petId(friendPartnerPet.getDog().getId())
                        .petName(friendPartnerPet.getName())
                        .petTier(friendPartnerPet.getTier())
                        .build();
                matesList.add(resMate);
            }

        }
        // 친구 목록이 없으면 빈리스트 반환
        if(matesList.isEmpty()) {
            return Collections.emptyList();
        }

        return matesList;
    }

    @Override // 친구삭제
    public void deleteMate(Long memberId, Long friendId) {
        // 멤버 엔티티 조회
        Member me = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        // 친구 엔티티 조회
        Member friend = memberRepository.findById(friendId).orElseThrow(MemberNotFoundException::new);

        Mate mate = null;
        mate = mateRepository.findByFriend1AndFriend2(me, friend);
        if(mate == null){
            mate = mateRepository.findByFriend2AndFriend1(me, friend);
        }
        // 친구관계가 존재하지 않을때
        if(mate == null){
            throw new MateNotFoundException();
        }
        // 이미 친구관계가 아닐때
        if(mate.getState() == NOTFRIEND){
            throw new AlreadyUnMateException();
        }

        Mate removeMate = Mate.builder()
                .Id(mate.getId())
                .friend1(mate.getFriend1())
                .friend2(mate.getFriend2())
                .state(NOTFRIEND)
                .build();
        mateRepository.save(removeMate);
    }

    @Override // 친구검색
    public List<SearchMates> getMembers(Long memId, String nickName) {
        // 멤버 엔티티 조회
        Member me = memberRepository.findById(memId).orElseThrow(MemberNotFoundException::new);
        // 검색목록 조회
        List<Member> searchMembers = memberRepository.findByNicknameContaining(nickName);

        // 검색 결과가 회원 1명도 없으면 빈리스트 반환
        if(searchMembers.size()==0){
            return Collections.emptyList();
        }

        Iterator<Member> iterator = searchMembers.iterator();

        List<SearchMates> members = new ArrayList<>();
        while (iterator.hasNext()) {
            Member member = iterator.next();

            // 나는 제외
            if (member.getId().equals(memId)) {
                iterator.remove();
                continue;
            }

            // 친구관계인지 확인
            Long mateId = null;
            State isFriend = null;
            Call call = null;
            Mate mate = null;
            mate = mateRepository.findByFriend1AndFriend2(me, member);
            if(mate == null){
                mate = mateRepository.findByFriend2AndFriend1(me, member);
            }

            // 검색 회원의 대표 반려견 조회
            MyPet friendPartnerPet = myPetRepository.findByMemberIdAndIsPartner(member.getId(), true)
                    .orElseThrow(PartnerPetNotFoundException::new);

            // 1. 친구테이블에 정보가 없을 때
            if(mate == null){
                mateId = (long) -1;
                isFriend = NOTFRIEND;
                call = Call.NO;
            }
            // 2. 친구테이블에 정보는 있지만 친구가 아닐 때
            else if(mate.getState() == NOTFRIEND){
                mateId = mate.getId();
                isFriend = NOTFRIEND;
                call = Call.NO;
            }
            // 3. 친구 요청중일 때
            else if(mate.getState() == WAITTING){ // 2. 친구요청중
                mateId = mate.getId();
                isFriend = WAITTING;

                // 친구신청을 받았는지 했는지 조회
                // 친구 테이블에서 조회한 객체의 friend1과 내 Id와 같으면 내가 발신자임
                if(mate.getFriend1().getId().equals(me.getId())){
                    call = Call.SENT;
                }else{
                    call = Call.RECEIVED;
                }
            }
            // 4. 친구일 때
            else{
                mateId = mate.getId();
                isFriend = FRIEND;
                call = Call.NO;
            }

            SearchMates searchMate = SearchMates.builder()
                    .mateId(mateId)
                    .memberId(member.getId())
                    .nickName(member.getNickname())
                    .state(isFriend)
                    .call(call)
                    .petId(friendPartnerPet.getDog().getId())
                    .petName(friendPartnerPet.getName())
                    .petTier(friendPartnerPet.getTier())
                    .build();

            members.add(searchMate);
        }

        return members;
    }


}
