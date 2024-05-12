package com.gaenari.backend.global.format.response;

import lombok.Data;

@Data
public class GenericResponse<T> {
    private String status;
    private String message;
    private T data;
}
