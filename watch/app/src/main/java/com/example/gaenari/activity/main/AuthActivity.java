package com.example.gaenari.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gaenari.databinding.ActivityAuthBinding;
import com.example.gaenari.dto.request.AuthRequestDto;
import com.example.gaenari.dto.response.ApiResponseDto;
import com.example.gaenari.dto.response.AuthResponseDto;
import com.example.gaenari.util.AccessToken;
import com.example.gaenari.util.MemberInfo;
import com.example.gaenari.util.PreferencesUtil;
import com.example.gaenari.util.Retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private ActivityAuthBinding binding;
    private MemberInfo member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.authId, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pref = PreferencesUtil.getEncryptedSharedPreferences(getApplicationContext());
//        startTimer();

        binding.authBtn.setOnClickListener(v -> {
            isVaildAuthCode();
        });
    }

    private void isVaildAuthCode() {
        AuthRequestDto requestDto = new AuthRequestDto(String.valueOf(binding.inputAuthcode.getText()));
        Log.i("Check", "Get AuthCode : " + requestDto.getAuthCode());

        Call<ApiResponseDto<AuthResponseDto>> call = Retrofit.getApiService().verifyCode(requestDto.getAuthCode());
        call.enqueue(new Callback<ApiResponseDto<AuthResponseDto>>() {

            @Override
            public void onResponse(Call<ApiResponseDto<AuthResponseDto>> call, Response<ApiResponseDto<AuthResponseDto>> response) {
                Log.i("Check", "Get AuthResponse : " + response);

                if (response.body().getStatus().equals("ERROR"))
                    Toast.makeText(AuthActivity.this, "인증번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                else {
                    if (response.isSuccessful() && response.body().getData() != null) {
                        Log.i("Check", "Get AccessToken : " + response.headers().get("authorization"));

                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("accessToken", response.headers().get("authorization"));
                        edit.apply();
                        AccessToken.getInstance().setAccessToken(response.headers().get("authorization"));

                        member = new MemberInfo();
                        member.getMemberInfo(getApplicationContext(), response.body().getData());

                        Toast.makeText(AuthActivity.this, "연동이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                        Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponseDto<AuthResponseDto>> call, Throwable t) {
                Toast.makeText(AuthActivity.this, "인증번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}