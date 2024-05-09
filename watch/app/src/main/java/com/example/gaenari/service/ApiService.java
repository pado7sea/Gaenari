package com.example.gaenari.service;

import com.example.gaenari.dto.request.AuthRequestDto;
import com.example.gaenari.dto.request.SaveDataRequestDto;
import com.example.gaenari.dto.response.ApiResponseDto;
import com.example.gaenari.dto.response.ApiResponseListDto;
import com.example.gaenari.dto.response.AuthResponseDto;
import com.example.gaenari.dto.response.FavoriteResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    /* 워치 연동 인증번호 확인 */
    @POST("member-service/watch")
    Call<ApiResponseDto<AuthResponseDto>> verifyCode(@Query("authCode") String request);

    /* 즐겨찾기 프로그램 조회 */
    @GET("program-service/program/favorite")
    Call<ApiResponseListDto<FavoriteResponseDto>> getFavoriteProgram(@Header("Authorization") String token);

    @POST("exercise-record-service/exercise/save")
    Call<ApiResponseDto<String>> saveRunningData(@Header("Authorization") String token, @Body SaveDataRequestDto data);

}
