package com.gaenari.backend.global.format.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericResponse<T> {
    private String status;
    private String message;
    private T data;
}
