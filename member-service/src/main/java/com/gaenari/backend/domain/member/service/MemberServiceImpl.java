package com.gaenari.backend.domain.member.service;

import com.gaenari.backend.domain.member.dto.MemberDto;
import com.gaenari.backend.domain.member.dto.requestDto.MemberUpdate;
import com.gaenari.backend.domain.member.dto.requestDto.MyPetDto;
import com.gaenari.backend.domain.member.dto.requestDto.SignupRequestDto;
import com.gaenari.backend.domain.member.dto.responseDto.SignupResponse;
import com.gaenari.backend.domain.member.entity.Member;
import com.gaenari.backend.domain.member.repository.MemberRepository;
import com.gaenari.backend.domain.mypet.entity.Dog;
import com.gaenari.backend.domain.mypet.entity.MyPet;
import com.gaenari.backend.domain.mypet.repository.DogRepository;
import com.gaenari.backend.domain.mypet.repository.MyPetRepository;
import com.gaenari.backend.global.exception.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final DogRepository dogRepository;
    private final MyPetRepository myPetRepository;

    @Override // 회원가입
    public SignupResponse createMember(SignupRequestDto requestDto) {
        // 이메일 중복 검증
        Member existMember = memberRepository.findByEmail(requestDto.getEmail());
        if (existMember != null) {
            throw new DuplicateEmailException();
        }

        // 엔티티에 회원등록
        Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickName())
                .birthday(requestDto.getBirth())
                .gender(requestDto.getGender())
                .height(requestDto.getHeight())
                .weight(requestDto.getWeight())
                .build();
        Member registMember = memberRepository.save(member);
        // 회원 반려견
        Dog dog = dogRepository.findById(requestDto.getMyPet().getId())
                .orElseThrow(DogNotFoundException::new);
        // 회원 반려견 등록
        MyPet myPet = MyPet.builder()
                .member(registMember)
                .dog(dog)
                .name(requestDto.getMyPet().getName())
                .isPartner(true)
                .build();
        MyPet registMyPet = myPetRepository.save(myPet);

        MyPetDto myPetDto = MyPetDto.builder()
                .id(registMyPet.getId())
                .name(registMyPet.getName())
                .build();

        // response
        SignupResponse signupResponse = SignupResponse.builder()
                .memberId(registMember.getId())
                .nickName(registMember.getNickname())
                .myPet(myPetDto)
                .build();

        return signupResponse;
    }

    @Override // 이메일로 회원 찾기
    public MemberDto getMemberDetailsByEmail(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail);
        List<MyPet> myPetList = myPetRepository.findByMemberId(member.getId()); // 멤버 아이디로 조회

        List<MyPetDto> myPetDtos = new ArrayList<>();
        for(MyPet pet : myPetList){
            MyPetDto myPet = MyPetDto.builder()
                    .id(pet.getId())
                    .name(pet.getName())
                    .build();

            myPetDtos.add(myPet);
        }

        MemberDto memberDto = MemberDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .gender(member.getGender())
                .height(member.getHeight())
                .weight(member.getWeight())
                .coin(member.getCoin())
                .lastTime(member.getLastTime())
                .myPetDto(myPetDtos)
                .build();

        return memberDto;
    }

    @Override // 마지막 접속시간 갱신
    public void updateLogoutTime(String memberEmail, LocalDateTime logoutTime) {
        Member member = memberRepository.findByEmail(memberEmail);

        if(member==null)
            throw new EmailNotFoundException();

        member = Member.builder()
                .Id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .gender(member.getGender())
                .height(member.getHeight())
                .weight(member.getWeight())
                .coin(member.getCoin())
                .device(member.getDevice())
                .lastTime(logoutTime)
                .build();
        memberRepository.save(member); // 변경 사항을 저장합니다.
    }

    @Override // 마지막 접속시간 반환
    public LocalDateTime getLastTime(String memberEmail) {
        Member mem = memberRepository.findByEmail(memberEmail);

        if(mem==null)
            throw new EmailNotFoundException();

        LocalDateTime time = mem.getLastTime();

        return time;
    }

    @Override // 회원 탈퇴
    public void deleteMember(String memberEmail) {
        // 회원 있는지 확인
        Member mem = memberRepository.findByEmail(memberEmail);

        memberRepository.delete(mem);
    }

    @Override // 보유코인 조회
    public int getCoin(String memberEmail) {
        Member mem = memberRepository.findByEmail(memberEmail);
        int coin = mem.getCoin();
        return coin;
    }

    @Override // 회원 닉네임 변경
    public void updateNick(String memberEmail, String nickName) {
        // 회원 조회
        Member member = memberRepository.findByEmail(memberEmail);

        if(member==null)
            throw new EmailNotFoundException();

        Member updateMember = Member.builder()
                .Id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(nickName) // 변경
                .birthday(member.getBirthday())
                .gender(member.getGender())
                .height(member.getHeight())
                .weight(member.getWeight())
                .coin(member.getCoin())
                .device(member.getDevice())
                .lastTime(member.getLastTime())
                .myPetList(member.getMyPetList())
                .build();
        memberRepository.save(updateMember);

    }

    @Override // 회원 비밀번호 변경
    public void updatePwd(String memberEmail, String newPassword) {
        // 회원 조회
        Member member = memberRepository.findByEmail(memberEmail);

        if(member==null)
            throw new EmailNotFoundException();

        Member updateMember = Member.builder()
                .Id(member.getId())
                .email(member.getEmail())
                .password(passwordEncoder.encode(newPassword)) // 변경
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .gender(member.getGender())
                .height(member.getHeight())
                .weight(member.getWeight())
                .coin(member.getCoin())
                .device(member.getDevice())
                .lastTime(member.getLastTime())
                .myPetList(member.getMyPetList())
                .build();
        memberRepository.save(updateMember);
    }

    @Override // 회원 정보 변경
    public void updateInfo(String memberEmail, MemberUpdate memberUpdate) {
        // 회원 조회
        Member member = memberRepository.findByEmail(memberEmail);

        if(member==null)
            throw new EmailNotFoundException();

        Member updateMember = Member.builder()
                .Id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .gender(member.getGender())
                .height(memberUpdate.getHeight()) // 변경
                .weight(memberUpdate.getWeight()) // 변경
                .coin(member.getCoin())
                .device(member.getDevice())
                .lastTime(member.getLastTime())
                .myPetList(member.getMyPetList())
                .build();
        memberRepository.save(updateMember);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username);

        if(member==null)
            throw new EmailNotFoundException();

        return new User(member.getEmail(), member.getPassword(),
                true, true, true, true,
                new ArrayList<>());
    }




}
