package com.gaenari.backend.global.exception.reward;

import com.gaenari.backend.global.format.code.ErrorCode;
import lombok.Getter;

@Getter
public class RewardNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public RewardNotFoundException() {this.errorCode = ErrorCode.REWARD_NOT_FOUND; }

}
