package com.gaenari.backend.domain.record.service.impl;

import com.gaenari.backend.domain.record.dto.responseDto.DailyRecordDto;
import com.gaenari.backend.domain.record.dto.responseDto.MonthRecordDto;
import com.gaenari.backend.domain.record.dto.responseDto.RecordDto;
import com.gaenari.backend.domain.record.dto.responseDto.WeekRecordDto;
import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.repository.RecordRepository;
import com.gaenari.backend.domain.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    @Override
    public List<RecordDto> getWholeExerciseRecords(Long memberId) {
        List<Record> records = recordRepository.findAllByMemberId(memberId); // 못찾으면 예외처리

        if (records.isEmpty()) {
            return Collections.emptyList();
        }

        return records.stream()
                .map(this::convertToRecordDto)
                .collect(Collectors.toList());
    }

    private RecordDto convertToRecordDto(Record record) {
        LocalDateTime localDateTime = record.getDate();

        DailyRecordDto dailyRecord = DailyRecordDto.builder()
                .recordId(record.getId())
                .recordDate(record.getDate())
                .recordTime(record.getTime())
                .recordDist(record.getDistance())
                .recordPace(record.getAveragePace())
                .build();

        return RecordDto.builder()
                .year(localDateTime.getYear())
                .month(localDateTime.getMonthValue())
                .day(localDateTime.getDayOfMonth())
                .dailyRecords(List.of(dailyRecord)) // 하루에 대한 단일 기록을 리스트로 설정
                .build();
    }

    @Override
    public MonthRecordDto getMonthlyExerciseRecords(Long memberId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        List<Record> records = recordRepository.findByMemberIdAndDateBetween(memberId, atStartOfDay(startDate),
                atEndOfDay(endDate).plusSeconds(1));

        if (records.isEmpty()) {
            return MonthRecordDto.builder()
                    .year(year)
                    .month(month)
                    .exerciseRecords(Collections.emptyList())
                    .build();
        }

        return convertToMonthRecordDto(year, month, endDate.getDayOfMonth(), records);
    }

    private MonthRecordDto convertToMonthRecordDto(int year, int month, int endDay, List<Record> records) {
        List<RecordDto> recordDtos = new ArrayList<>();
        for (int day = 1; day <= endDay; day++) {
            final int d = day;
            List<DailyRecordDto> dailyRecords = records.stream()
                    .filter(r -> r.getDate().getDayOfMonth() == d)
                    .map(r -> DailyRecordDto.builder()
                            .recordId(r.getId())
                            .recordDate(r.getDate())
                            .recordTime(r.getTime())
                            .recordDist(r.getDistance())
                            .recordPace(r.getAveragePace())
                            .build())
                    .toList();

            recordDtos.add(RecordDto.builder()
                    .year(year)
                    .month(month)
                    .day(day)
                    .dailyRecords(dailyRecords)
                    .build());
        }

        return MonthRecordDto.builder()
                .year(year)
                .month(month)
                .exerciseRecords(recordDtos)
                .build();
    }

    @Override
    public WeekRecordDto getWeeklyExerciseRecords(Long memberId, int year, int month, int day) {
        LocalDate baseDate = LocalDate.of(year, month, day);
        LocalDate startDate = baseDate.with(ChronoField.DAY_OF_WEEK, 1); // 주의 시작 (일요일)
        LocalDate endDate = startDate.plusDays(6); // 주의 끝 (토요일)

        List<Record> records = recordRepository.findByMemberIdAndDateBetween(memberId, atStartOfDay(startDate),
                atEndOfDay(endDate).plusSeconds(1));

        if (records.isEmpty()) {
            return WeekRecordDto.builder()
                    .year(startDate.getYear())
                    .month(startDate.getMonthValue())
                    .exerciseRecords(Collections.emptyList())
                    .build();
        }

        return buildWeekRecordDto(records, startDate);
    }

    private WeekRecordDto buildWeekRecordDto(List<Record> records, LocalDate startDate) {
        Map<LocalDate, List<DailyRecordDto>> dailyRecords = records.stream()
                .collect(Collectors.groupingBy(
                        record -> record.getDate().toLocalDate(),
                        Collectors.mapping(this::convertToDailyRecordDto, Collectors.toList())
                ));

        List<RecordDto> weeklyRecords = startDate.datesUntil(startDate.plusDays(7))
                .map(date -> RecordDto.builder()
                        .year(date.getYear())
                        .month(date.getMonthValue())
                        .day(date.getDayOfMonth())
                        .dailyRecords(dailyRecords.getOrDefault(date, Collections.emptyList()))
                        .build())
                .toList();

        return WeekRecordDto.builder()
                .year(startDate.getYear())
                .month(startDate.getMonthValue())
                .exerciseRecords(weeklyRecords)
                .build();
    }

    private DailyRecordDto convertToDailyRecordDto(Record record) {
        return DailyRecordDto.builder()
                .recordId(record.getId())
                .recordDate(record.getDate())
                .recordTime(record.getTime())
                .recordDist(record.getDistance())
                .recordPace(record.getAveragePace())
                .build();
    }

    @Override
    public List<RecordDto> getDailyExerciseRecords(Long memberId, LocalDate date) {
        List<Record> records = recordRepository.findByMemberIdAndDateBetween(memberId, atStartOfDay(date),
                atEndOfDay(date).plusSeconds(1));

        return records.stream()
                .map(this::convertToRecordDto)
                .toList();
    }

    private LocalDateTime atStartOfDay(LocalDate date) {
        return date.atStartOfDay();  // 날짜의 시작 시간
    }

    private LocalDateTime atEndOfDay(LocalDate date) {
        return date.atTime(23, 59, 59, 999999999);  // 날짜의 마지막 시간
    }

}