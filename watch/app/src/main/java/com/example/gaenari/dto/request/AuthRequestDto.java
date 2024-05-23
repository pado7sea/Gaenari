package com.example.gaenari.dto.request;

public class AuthRequestDto {

    private String authCode;

    public AuthRequestDto() {
    }

    public AuthRequestDto(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        return "LoginRequestDto{" +
                "authCode='" + authCode + '\'' +
                '}';
    }
}
