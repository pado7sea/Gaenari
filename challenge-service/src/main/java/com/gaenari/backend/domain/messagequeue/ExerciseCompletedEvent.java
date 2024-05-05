package com.gaenari.backend.domain.messagequeue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseCompletedEvent {
    private String memberId;
}
