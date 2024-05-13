package com.gaenari.backend.domain.statistic.service.impl;

import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.repository.RecordRepository;
import com.gaenari.backend.domain.statistic.dto.responseDto.MonthStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.TotalStatisticDto;
import com.gaenari.backend.domain.statistic.dto.responseDto.WeekStatisticDto;
import com.gaenari.backend.domain.statistic.entity.Statistic;
import com.gaenari.backend.domain.statistic.repository.StatisticRepository;
import com.gaenari.backend.domain.statistic.service.StatisticService;
import com.gaenari.backend.global.exception.statistic.StatisticNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final RecordRepository recordRepository;
    private final StatisticRepository statisticRepository;

    // 회원 ID에 해당하는 모든 운동 기록을 조회한 후 그 결과를 이용해 총 합계와 평균을 계산하는 방식(데이터베이스 저장x)
    @Override
    public TotalStatisticDto getWholeExerciseStatistics(String memberId) {
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

        LocalDateTime mostRecentDate = getMostRecentDate(records);

        return TotalStatisticDto.builder()
                .time(totalTime)
                .dist(totalDistance)
                .cal(totalCalories)
                .pace(count > 0 ? totalPace / count : 0)
                .date(mostRecentDate)
                .count(count)
                .build();
    }

    private LocalDateTime getMostRecentDate(List<Record> records) {
        return records.stream()
                .map(Record::getDate) // Record 객체에서 날짜를 추출
                .max(Comparator.naturalOrder())// 날짜 중 가장 최근 날짜를 찾음
                .orElse(null);
    }

    // 저장되어있는 누적값 조회
    @Override
    public TotalStatisticDto getTotalStatistics(String memberId) {
       Statistic statistic = statisticRepository.findByMemberId(memberId);

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
    public MonthStatisticDto getMonthlyExerciseStatistics(String memberId, int year, int month) {
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

    // 주간 통계 조회
    @Override
    public WeekStatisticDto getWeeklyExerciseStatistics(String memberId, int year, int month, int day) {
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
