package com.gaenari.backend.domain.statistic.service.impl;

import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.repository.RecordRepository;
import com.gaenari.backend.domain.statistic.dto.responseDto.MonthStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.WeekStatisticDto;
import com.gaenari.backend.domain.statistic.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final RecordRepository recordRepository;

    @Override
    public TotalStatisticDto getWholeExerciseStatistics(Long memberId) {
        List<Record> records = recordRepository.findAllByMemberId(memberId);
        return calculateTotalStatistics(records);
    }

    private TotalStatisticDto calculateTotalStatistics(List<Record> records) {
        double totalTime = 0, totalDistance = 0, totalCalories = 0, totalPace = 0, totalHeartRate = 0;
        int count = records.size();

        for (Record record : records) {
            totalTime += record.getTime();
            totalDistance += record.getDistance();
            totalCalories += record.getCal();
            totalPace += record.getAveragePace();
            totalHeartRate += record.getAverageHeartRate();
        }

        return TotalStatisticDto.builder()
                .time(totalTime)
                .dist(totalDistance)
                .cal(totalCalories)
                .pace(count > 0 ? totalPace / count : 0)
                .build();
    }

    @Override
    public MonthStatisticDto getMonthlyExerciseStatistics(Long memberId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Record> records = recordRepository.findByMemberIdAndDateBetween(memberId, atStartOfDay(startDate),
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

    @Override
    public WeekStatisticDto getWeeklyExerciseStatistics(Long memberId, int year, int month, int day) {
        LocalDate baseDate = LocalDate.of(year, month, day);
        LocalDate startDate = baseDate.with(ChronoField.DAY_OF_WEEK, 1);  // 주 시작 (일요일)
        LocalDate endDate = startDate.plusDays(6);  // 주 끝 (토요일)

        List<Record> records = recordRepository.findByMemberIdAndDateBetween(memberId, atStartOfDay(startDate),
                atEndOfDay(endDate).plusSeconds(1));

        return buildWeekStatistics(records, startDate);
    }

    private WeekStatisticDto buildWeekStatistics(List<Record> records, LocalDate startDate) {
        double totalTime = 0, totalDistance = 0, totalCalories = 0, totalPace = 0, totalHeartRate = 0;
        int count = records.size();

        for (Record record : records) {
            totalTime += record.getTime();
            totalDistance += record.getDistance();
            totalCalories += record.getCal();
            totalPace += record.getAveragePace();
            totalHeartRate += record.getAverageHeartRate();
        }

        return WeekStatisticDto.builder()
                .time(totalTime)
                .dist(totalDistance)
                .cal(totalCalories)
                .pace(count > 0 ? totalPace / count : 0)
                .heart(count > 0 ? totalHeartRate / count : 0)
                .build();
    }

    private LocalDateTime atStartOfDay(LocalDate date) {
        return date.atStartOfDay();  // 날짜의 시작 시간
    }

    private LocalDateTime atEndOfDay(LocalDate date) {
        return date.atTime(23, 59, 59, 999999999);  // 날짜의 마지막 시간
    }

}
