package com.kurly.product.dto;

public class ApiResponse {
    private boolean success;
    private String message;
    private boolean error;
    private String code;

    // 기본 생성자
    public ApiResponse() {}

    // 성공 응답 생성자
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.error = !success;
    }

    // 에러 응답 생성자
    public ApiResponse(boolean error, String message, String code) {
        this.error = error;
        this.message = message;
        this.code = code;
        this.success = !error;
    }

    // 정적 팩토리 메서드
    public static ApiResponse success(String message) {
        return new ApiResponse(true, message);
    }

    public static ApiResponse error(String message, String code) {
        return new ApiResponse(true, message, code);
    }

    // Getter, Setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
