package com.gaenari.backend.domain.messagequeue;

import com.gaenari.backend.domain.afterExercise.dto.requestDto.SaveExerciseRecordDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseCompletedEventDto {
    private Long memberId;
    private SaveExerciseRecordDto exerciseDto;

}
