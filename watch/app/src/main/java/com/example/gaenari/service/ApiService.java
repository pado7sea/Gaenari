package com.example.gaenari.service;

import com.example.gaenari.dto.request.AuthRequestDto;
import com.example.gaenari.dto.response.ApiResponseDto;
import com.example.gaenari.dto.response.AuthResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

//    @POST("/member-service/member/watch")
//    Call<ApiResponseDto<AuthResponseDto>> verifyCode(@Body AuthRequestDto requestDto);
    @POST("member-service/watch")
    Call<ApiResponseDto<AuthResponseDto>> verifyCode(@Query("authCode") String re);
}
