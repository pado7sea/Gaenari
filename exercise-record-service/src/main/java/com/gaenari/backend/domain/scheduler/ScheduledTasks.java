package com.gaenari.backend.domain.scheduler;

import com.gaenari.backend.domain.client.member.MemberServiceClient;
import com.gaenari.backend.domain.client.member.dto.HeartChangeDto;
import com.gaenari.backend.domain.statistic.entity.Statistic;
import com.gaenari.backend.domain.statistic.repository.StatisticRepository;
import com.gaenari.backend.global.exception.feign.ConnectFeignFailException;
import com.gaenari.backend.global.format.response.GenericResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ScheduledTasks {

    private final MemberServiceClient memberServiceClient;
    private final StatisticRepository statisticRepository;


    @Scheduled(cron = "${scheduler.cron.expression}") // 설정 파일에서 정의된 주기(cron.expression)에 따라 실행
    public void decreaseHeartsForInactiveUsers() {

        int heartDecreaseAmount = 10;
        int inactivityWeeks = 1;

        // 설정된 기간(inactivity.weeks) 동안 활동하지 않은 사용자들의 ID를 조회하여 그들의 애정도를 감소
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(inactivityWeeks);
        List<String> inactiveMemberIds = statisticRepository.findByDateBefore(oneWeekAgo)
                .stream()
                .map(Statistic::getMemberId)
                .toList();

        for (String memberId : inactiveMemberIds) {
            decreaseHeartForMember(memberId, heartDecreaseAmount);
        }
    }

    private void decreaseHeartForMember(String memberId, int amount) {
        ResponseEntity<GenericResponse<?>> response = memberServiceClient.updateHeart(new HeartChangeDto(memberId, false, amount));
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ConnectFeignFailException();
        }
        System.out.println("Heart decreased for: " + memberId + ", amount: " + amount);
    }
}
