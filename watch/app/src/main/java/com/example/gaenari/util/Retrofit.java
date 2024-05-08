package com.example.gaenari.util;

import com.example.gaenari.adapter.LocalDateTimeAdapter;
import com.example.gaenari.service.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    private static final String BASE_URL = "https://api.gaenari.kr/api/";
    private static retrofit2.Retrofit retrofit = null;

    public static ApiService getApiService() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .addInterceptor(new BrotliInterceptor())
                    .build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit.create(ApiService.class);
    }
}
