package com.moviemang.coreutils.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@SuppressWarnings("rawtypes")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    @JsonIgnore
    private HttpStatus status;
	private Result result;
    private T data;
    private String message;
    private String errorCode;

    @SuppressWarnings("unchecked")
	public static <T> CommonResponse<T> success(T data, String message) {
        return (CommonResponse<T>) CommonResponse.builder()
                .result(Result.SUCCESS)
                .data(data)
                .message(message)
                .build();
    }

    public static <T> CommonResponse<T> success(ErrorCode errorCode) {
        return (CommonResponse<T>) CommonResponse.builder()
                .result(Result.SUCCESS)
                .message(errorCode.getErrorMsg())
                .build();
    }
    @SuppressWarnings("unchecked")
    public static <T> CommonResponse<T> success(T data, String message, HttpStatus status) {
        return (CommonResponse<T>) CommonResponse.builder()
                .result(Result.SUCCESS)
                .data(data)
                .message(message)
                .status(status)
                .build();
    }

    public static <T> CommonResponse<T> success(T data) {
        return success(data, null);
    }

	public static CommonResponse fail(String message, String errorCode) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .message(message)
                .errorCode(errorCode)
                .build();
    }

    public static CommonResponse fail(ErrorCode errorCode) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .message(errorCode.getErrorMsg())
                .errorCode(errorCode.name())
                .build();
    }
    public static CommonResponse fail(ErrorCode errorCode, HttpStatus status) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .status(status)
                .message(errorCode.getErrorMsg())
                .errorCode(errorCode.name())
                .build();
    }

    public enum Result {
        SUCCESS, FAIL
    }
}
