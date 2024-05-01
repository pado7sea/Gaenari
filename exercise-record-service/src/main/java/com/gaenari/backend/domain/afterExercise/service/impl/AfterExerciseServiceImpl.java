package com.gaenari.backend.domain.afterExercise.service.impl;

import com.gaenari.backend.domain.afterExercise.dto.requestDto.SaveExerciseRecordDto;
import com.gaenari.backend.domain.afterExercise.service.AfterExerciseService;
import com.gaenari.backend.domain.record.dto.enumType.ExerciseType;
import com.gaenari.backend.domain.record.dto.enumType.ProgramType;
import com.gaenari.backend.domain.record.entity.IntervalRangeRecord;
import com.gaenari.backend.domain.record.entity.Moment;
import com.gaenari.backend.domain.record.entity.Record;
import com.gaenari.backend.domain.record.repository.RecordRepository;
import com.gaenari.backend.global.exception.program.ProgramNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AfterExerciseServiceImpl implements AfterExerciseService {

    private final RecordRepository recordRepository;

    @Override
    public Long saveExerciseRecord(Long memberId, SaveExerciseRecordDto exerciseDto) {
        ExerciseType exerciseType = ExerciseType.valueOf(exerciseDto.getExerciseType());
        ProgramType programType = ProgramType.valueOf(exerciseDto.getProgramType());

        List<IntervalRangeRecord> ranges = new ArrayList<>();
        SaveExerciseRecordDto.ProgramDto programDto = exerciseDto.getProgram();
        if (programDto != null && programDto.getIntervalInfo() != null) {
            programDto.getIntervalInfo().getRanges().forEach(rangeDto -> {
                ranges.add(IntervalRangeRecord.builder()
                        .memberId(memberId)
                        .isRunning(rangeDto.getIsRunning())
                        .time(rangeDto.getTime())
                        .speed(rangeDto.getSpeed())
                        .build());
            });
        }

        List<Moment> moments = new ArrayList<>();
        if (exerciseDto.getRecord().getTime() != null) {
            for (int i = 0; i < exerciseDto.getSpeeds().getArr().size(); i++) {
                int speed = exerciseDto.getSpeeds().getArr().get(i);
                Integer heartrate = exerciseDto.getHeartrates().getArr().size() > i ? exerciseDto.getHeartrates().getArr().get(i) : null;
                moments.add(Moment.builder()
                        .heartrate(heartrate)
                        .distance(speed * 60 / 3600) // 시속 km/h를 m/min로 변환
                        .pace(speed == 0 ? null : 3600 / speed) // 분당 페이스 계산, 속도가 0이면 null로 설정
                        .build());
            }
        }

        if (exerciseDto.getProgram() == null) {
            throw new ProgramNotFoundException();
        }

        Record record = Record.builder()
                .memberId(memberId)
                .exerciseType(exerciseType)
                .programType(programType)
                .programId(exerciseDto.getProgram().getProgramId())
                .date(exerciseDto.getDate())
                .time(exerciseDto.getRecord().getTime())
                .distance(exerciseDto.getRecord().getDistance())
                .averagePace((3600 / exerciseDto.getSpeeds().getAverage()))
                .averageHeartRate(exerciseDto.getHeartrates().getAverage())
                .Cal(exerciseDto.getRecord().getCal())
                .isFinished(true)
                .ranges(ranges)
                .moments(moments)
                .build();

        ranges.forEach(range -> range.updateRecord(record));
        moments.forEach(moment -> moment.updateRecord(record));

        return recordRepository.save(record).getId();
    }


}
// TODO 포문돌 때 오늘날짜 이전 날짜까지로 예외처리해두기