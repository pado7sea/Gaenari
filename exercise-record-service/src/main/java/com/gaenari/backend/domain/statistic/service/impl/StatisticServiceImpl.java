package com.gaenari.backend.domain.statistic.service.impl;

import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.repository.RecordRepository;
import com.gaenari.backend.domain.statistic.dto.responseDto.*;
import com.gaenari.backend.domain.statistic.entity.Statistic;
import com.gaenari.backend.domain.statistic.repository.StatisticRepository;
import com.gaenari.backend.domain.statistic.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final RecordRepository recordRepository;
    private final StatisticRepository statisticRepository;

    // 저장되어있는 회원의 누적 통계 값 조회
    @Override
    public TotalStatisticDto getTotalStatistics(String accountId) {
        Statistic statistic = statisticRepository.findByAccountId(accountId);

        // 데이터가 없는 경우 빈 객체 반환
        if (statistic == null) {
            return TotalStatisticDto.builder()
                    .time(0.0)
                    .dist(0.0)
                    .cal(0.0)
                    .pace(0.0)
                    .date(null)  // 또는 적절한 기본 날짜
                    .count(0)
                    .build();
        }

        return TotalStatisticDto.builder()
                .time(statistic.getTime())
                .dist(statistic.getDist())
                .cal(statistic.getCal())
                .pace(statistic.getPace())
                .date(statistic.getDate())
                .count(statistic.getCount())
                .build();
    }

    // 월간 통계 조회
    @Override
    public MonthStatisticDto getMonthlyExerciseStatistics(String accountId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Record> records = recordRepository.findByAccountIdAndDateBetween(accountId, atStartOfDay(startDate),
                atEndOfDay(endDate).plusSeconds(1));

        return calculatePeriodStatistics(records, year, month);
    }

    private MonthStatisticDto calculatePeriodStatistics(List<Record> records, int year, int month) {
        double totalTime = 0, totalDistance = 0, totalCalories = 0, totalPace = 0, totalHeartRate = 0;
        int count = records.size();

        for (Record record : records) {
            totalTime += record.getTime();
            totalDistance += record.getDistance();
            totalCalories += record.getCal();
            totalPace += record.getAveragePace();
            totalHeartRate += record.getAverageHeartRate();
        }

        return MonthStatisticDto.builder()
                .year(year)
                .month(month)
                .time(totalTime)
                .dist(totalDistance)
                .cal(totalCalories)
                .pace(count > 0 ? (totalPace / count) : 0)
                .heart(count > 0 ? (totalHeartRate / count) : 0)
                .build();
    }

    // 주간 통계 조회
    @Override
    public List<StatisticDto> getWeeklyExerciseStatistics(String accountId, int year, int month, int day) {
        LocalDate baseDate = LocalDate.of(year, month, day);
        LocalDate startDate = baseDate.with(ChronoField.DAY_OF_WEEK, 1);  // 주의 시작 (일요일)
        LocalDate endDate = startDate.plusDays(6);  // 주의 끝 (토요일)

        List<Record> records = recordRepository.findByAccountIdAndDateBetween(accountId, atStartOfDay(startDate),
                atEndOfDay(endDate).plusSeconds(1));

        List<StatisticDto> weeklyStatistics = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            DailyStatisticDto dailyStatistic = calculateDailyStatistics(records, currentDate);
            weeklyStatistics.add(
                    StatisticDto.builder()
                            .date(currentDate)
                            .dailyStatistic(dailyStatistic)
                            .build()
            );
        }

        return weeklyStatistics;
    }


    private DailyStatisticDto calculateDailyStatistics(List<Record> records, LocalDate currentDate) {
        double totalTime = 0, totalDistance = 0, totalCalories = 0, totalPace = 0, totalHeartRate = 0;
        int count = 0;

        for (Record record : records) {
            LocalDate recordDate = record.getDate().toLocalDate();
            if (recordDate.equals(currentDate)) {
                totalTime += record.getTime();
                totalDistance += record.getDistance();
                totalCalories += record.getCal();
                totalPace += record.getAveragePace();
                totalHeartRate += record.getAverageHeartRate();
                count++;
            }
        }

        if (count > 0) {
            double averagePace = totalPace / count;
            double averageHeartRate = totalHeartRate / count;
            return DailyStatisticDto.builder()
                    .time(totalTime)
                    .dist(totalDistance)
                    .cal(totalCalories)
                    .pace(averagePace)
                    .heart(averageHeartRate)
                    .build();
        } else {
            // 해당 날짜에 운동 기록이 없을 경우 빈 DailyStatisticDto를 생성.
            return DailyStatisticDto.builder()
                    .time(0.0)
                    .dist(0.0)
                    .cal(0.0)
                    .pace(0.0)
                    .heart(0.0)
                    .build();
        }
    }

    private LocalDateTime atStartOfDay(LocalDate date) {
        return date.atStartOfDay();  // 날짜의 시작 시간
    }

    private LocalDateTime atEndOfDay(LocalDate date) {
        return date.atTime(23, 59, 59, 999999999);  // 날짜의 마지막 시간
    }

}
