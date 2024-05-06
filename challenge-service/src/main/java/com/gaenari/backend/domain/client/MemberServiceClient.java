package com.gaenari.backend.domain.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    // TODO : member-service로 애정도랑 코인 증가시기키 포스트? 풋?

    // TODO : 최근 운동기록 날짜 기준으로 일정 기간 지나면 애정도 떨어뜨리는 스케줄 작성. 애정도 감소 요청 보내기
    // 아닌가.. 최근 접속 시간 -> 로그인 or 운동기록 이렇게 해야하나?
    // 이거 레코드 서비스에서 멤버서비스로 요청보내는 걸로 위치 바꿔야겠다.
}
