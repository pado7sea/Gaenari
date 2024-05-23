package com.gaenari.backend.global.format.response;

import com.gaenari.backend.global.format.code.ErrorCode;
import com.gaenari.backend.global.format.code.ResponseCode;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiResponse {

    private static ApiResponse instance;

    public static ApiResponse getInstance() {
        if (instance == null) {
            instance = new ApiResponse();
        }
        return instance;
    }
    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_FAIL = "FAILED";
    private static final String STATUS_ERROR = "ERROR";

    private <T, E> ResponseEntity<?> get(
            String status,
            @Nullable String message,
            @Nullable T data,
            @Nullable E errors
    ) {
        if (status.equals(STATUS_SUCCESS)) {
            return new ResponseEntity<>(SucceededBody.builder()
                    .status(status)
                    .message(message)
                    .data(data)
                    .build(),
                    HttpStatus.OK);
        } else if (status.equals(STATUS_FAIL)) {
            return new ResponseEntity<>(FailedBody.builder()
                    .status(status)
                    .message(message)
                    .errors(errors)
                    .build(),
                    HttpStatus.OK);
        } else if (status.equals(STATUS_ERROR)) {
            return new ResponseEntity<>(ErroredBody.builder()
                    .status(status)
                    .message(message)
                    .build(),
                    HttpStatus.OK);
        } else {
            throw new RuntimeException("Api Response Error");
        }
    }

    @Builder
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SucceededBody<T> {

        private String status;
        private String message;
        private T data;
    }

    @Builder
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FailedBody<E> {

        private String status;
        private String message;
        private E errors;
    }

    @Builder
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErroredBody {

        private String status;
        private String message;
    }

    /**
     * <p>필드 에러 출력에 사용할 객체</p>
     */
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldError {

        private String field;
        private String message;

        public FieldError(ObjectError objectError) {
            this.field = objectError.getObjectName();
            this.message = objectError.getDefaultMessage();
        }
    }

    /**
     * <p>성공 응답을 반환합니다. 첫 번째 인자는 message, 두 번째 인자는 data 에 표시됩니다.</p>
     * <pre>
     *  {
     *      "status" : "SUCCESS",
     *      "message" : "success message",
     *      "data" : "배열 또는 단일 데이터",
     *      "errors" : null
     *  }
     * </pre>
     */
    public <T> ResponseEntity<?> success(ResponseCode responseCode, T data) {
        return get(STATUS_SUCCESS, responseCode.getMessage(), data, null);
    }

    /**
     * <p>성공 응답을 반환합니다. 전달된 인자는 data 에 표시됩니다.</p>
     * <pre>
     *  {
     *      "status" : "SUCCESS",
     *      "message" : null,
     *      "data" : "배열 또는 단일 데이터",
     *      "errors" : null
     *  }
     * </pre>
     *
     * @param data 응답 바디 data 필드에 포함될 정보
     * @return 응답 객체
     */
    public <T> ResponseEntity<?> success(T data) {
        return get(STATUS_SUCCESS, null, data, null);
    }

    /**
     * <p>성공 응답을 반환합니다. 전달된 인자는 data 에 표시됩니다.</p>
     * <pre>
     *  {
     *      "status" : "SUCCESS",
     *      "message" : success message,
     *      "data" : null,
     *      "errors" : null
     *  }
     * </pre>
     */
    public <T> ResponseEntity<?> success(ResponseCode responseCode) {
        return get(STATUS_SUCCESS, responseCode.getMessage(), null, null);
    }

    /**
     * <p>성공 응답을 반환합니다.</p>
     * <pre>
     *  {
     *      "status" : "SUCCESS",
     *      "message" : null,
     *      "data" : null,
     *      "errors" : null
     *  }
     * </pre>
     *
     * @return 응답 객체
     */
    public <T> ResponseEntity<?> success() {
        return get(STATUS_SUCCESS, null, null, null);
    }

    /**
     * <p>오류 발생 시 실패 응답을 반환합니다.</p>
     * <pre>
     *     {
     *         "status" : "FAILED",
     *         "message" : "fail message",
     *         "data" : null,
     *         "errors" : null
     *     }
     * </pre>
     *
     * @param message 응답 바디 message 필드에 포함될 정보
     * @return 응답 객체
     */
    public <T> ResponseEntity<?> fail(String message) {
        return get(STATUS_FAIL, message, null, null);
    }

    /**
     * <p>필드 에러로 인한 실패 응답을 반환합니다.</p>
     * <pre>
     *     {
     *         "status" : "FAILED",
     *         "message" : fail message,
     *         "data" : null,
     *         "errors" : [{error data1}, {error data2} ... ]
     *     }
     * </pre>
     *
     * @param message 응답 바디 message 필드에 포함될 정보
     * @param errors  응답 바디 errors 필드에 포함될 정보
     * @return 응답 객체
     */
    public <E> ResponseEntity<?> fail(String message, E errors) {
        return get(STATUS_FAIL, message, null, errors);
    }

    /**
     * <p>필드 에러로 인한 실패 응답을 반환합니다.</p>
     * <pre>
     *     {
     *         "status" : "FAILED",
     *         "message" : fail message,
     *         "data" : null,
     *         "errors" : [{error data1}, {error data2} ... ]
     *     }
     * </pre>
     *
     * @param errors 응답 바디 errors 필드에 포함될 정보
     * @return 응답 객체
     */
    public ResponseEntity<?> fail(Errors errors) {
        List<FieldError> fieldErrorList = errors.getAllErrors().stream().map(FieldError::new)
                .collect(Collectors.toList());
        return fail(null, fieldErrorList);
    }

    /**
     * <p>필드 에러로 인한 실패 응답을 반환합니다.</p>
     * <pre>
     *     {
     *         "status" : "FAILED",
     *         "message" : fail message,
     *         "data" : null,
     *         "errors" : [{error data1}, {error data2} ... ]
     *     }
     * </pre>
     *
     * @param bindingResult 응답 바디 errors 필드에 포함될 정보를 가진 BindingResult 객체
     * @return 응답 객체
     */
    public ResponseEntity<?> fail(BindingResult bindingResult) {
        return fail((Errors) bindingResult);
    }

    /**
     * <p>필드 에러로 인한 실패 응답을 반환합니다.</p>
     * <pre>
     *     {
     *         "status" : "FAILED",
     *         "message" : null,
     *         "data" : null,
     *         "errors" : [{error data1}, {error data2} ... ]
     *     }
     * </pre>
     *
     * @param errors 응답 바디 errors 필드에 포함될 정보
     * @return 응답 객체
     */
    public <E> ResponseEntity<?> fail(E errors) {
        return get(STATUS_FAIL, null, null, errors);
    }

    /**
     * <p>예외 발생 시 에러 응답을 반환합니다.</p>
     * <pre>
     *     {
     *         "status" : "ERROR",
     *         "message" : "custom error message",
     *         "data" : null,
     *         "errors" : null
     *     }
     * </pre>
     *
     * @param message 응답 바디 message 필드에 포함될 정보
     * @return 응답 객체
     */
    public <T> ResponseEntity<?> error(String message) {
        return get(STATUS_ERROR, message, null, null);
    }

    /**
     * <p>예외 발생 시 에러 응답을 반환합니다.</p>
     * <pre>
     *     {
     *         "status" : "ERROR",
     *         "message" : "Custom ErrorCode Message",
     *         "data" : null,
     *         "errors" : null
     *     }
     * </pre>
     */
    public <T> ResponseEntity<?> error(ErrorCode errorCode) {
        return get(STATUS_ERROR, errorCode.getMessage(), null, null);
    }


}
