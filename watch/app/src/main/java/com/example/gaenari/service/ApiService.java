package com.example.gaenari.service;

import com.example.gaenari.dto.request.AlertStartRequestDto;
import com.example.gaenari.dto.request.SaveDataRequestDto;
import com.example.gaenari.dto.response.ApiResponseDto;
import com.example.gaenari.dto.response.ApiResponseListDto;
import com.example.gaenari.dto.response.AuthResponseDto;
import com.example.gaenari.dto.response.FavoriteResponseDto;
import com.example.gaenari.dto.response.MyPetResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * 워치 연동 인증번호 확인
     * @param request AuthCode
     * @return ApiResponseDto(AuthResponseDto)
     */
    @POST("member-service/watch")
    Call<ApiResponseDto<AuthResponseDto>> verifyCode(@Query("authCode") String request);

    /**
     * 즐겨찾기 프로그램 조회
     * @param token AccessToken
     * @return ApiResponseListDto(FavoriteResponseDto)
     */
    @GET("program-service/program/favorite")
    Call<ApiResponseListDto<FavoriteResponseDto>> getFavoriteProgram(@Header("Authorization") String token);

    /**
     * 운동 기록 저장
     * @param token AccessToken
     * @param data SaveDataRequestDto
     * @return ApiResponseDto(String)
     */
    @POST("exercise-record-service/exercise/save")
    Call<ApiResponseDto<String>> saveRunningData(@Header("Authorization") String token, @Body SaveDataRequestDto data);

    /**
     * 강아지 정보 업데이트
     * @param token AccessToken
     * @return ApiResponseDto(MyPetResponseDto)
     */
    @GET("member-service/pet/partner")
    Call<ApiResponseDto<MyPetResponseDto>> getMyPetInfo(@Header("Authorization") String token);

    /**
     * 운동 시작 알림 전송
     * @param token AccessToken
     * @param request AlertStartRequestDto
     * @return ApiResponseDto(String)
     */
    @POST("exercise-record-service/exercise/alert")
    Call<ApiResponseDto<String>> alertStartExercise(@Header("Authorization") String token, @Body AlertStartRequestDto request);
}
