package com.example.gaenari.activity.main;

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gaenari.databinding.ActivityAuthBinding
import com.example.gaenari.dto.request.AuthRequestDto
import com.example.gaenari.dto.response.ApiResponseDto
import com.example.gaenari.dto.response.AuthResponseDto
import com.example.gaenari.util.AccessToken
import com.example.gaenari.util.MemberInfo
import com.example.gaenari.util.PreferencesUtil
import com.example.gaenari.util.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.String
import kotlin.Throwable
import kotlin.Unit


class AuthActivity : AppCompatActivity() {
    private var pref: SharedPreferences? = null
    private var binding: ActivityAuthBinding? = null
    private var member: MemberInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding!!.authId) { v: View, insets: WindowInsetsCompat ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        pref = PreferencesUtil.getEncryptedSharedPreferences(applicationContext)
        //        startTimer();
        binding!!.authBtn.setOnClickListener { v -> isVaildAuthCode }
    }

    private val isVaildAuthCode: Unit
        private get() {
            val requestDto = AuthRequestDto(String.valueOf(binding!!.inputAuthcode.getText()))
            Log.i("Check", "Get AuthCode : " + requestDto.authCode)
            val call = Retrofit.getApiService().verifyCode(requestDto.authCode)
            call.enqueue(object : Callback<ApiResponseDto<AuthResponseDto?>> {
                override fun onResponse(
                    call: Call<ApiResponseDto<AuthResponseDto?>>,
                    response: Response<ApiResponseDto<AuthResponseDto?>>
                ) {
                    Log.i("Check", "Get AuthResponse : $response")
                    if (response.body()!!.status == "ERROR") Toast.makeText(
                        this@AuthActivity,
                        "인증번호를 다시 확인해주세요.",
                        Toast.LENGTH_SHORT
                    ).show() else {
                        if (response.isSuccessful && response.body()!!.data != null) {
                            Log.i(
                                "Check",
                                "Get AccessToken : " + response.headers()["authorization"]
                            )
                            val edit = pref!!.edit()
                            edit.putString("accessToken", response.headers()["authorization"])
                            edit.apply()
                            AccessToken.getInstance().accessToken =
                                response.headers()["authorization"]
                            member = MemberInfo()
                            member!!.getMemberInfo(applicationContext, response.body()!!.data)
                            Toast.makeText(this@AuthActivity, "연동이 완료되었습니다.", Toast.LENGTH_SHORT)
                                .show()
                            setResult(RESULT_OK)
                            val intent = Intent(
                                this@AuthActivity,
                                HomeActivity::class.java
                            )
                            HomeActivity.finishHomeActivity()
                            startActivity(intent)
                            finish()
                        }
                    }
                }

                override fun onFailure(call: Call<ApiResponseDto<AuthResponseDto?>>, t: Throwable) {
                    Toast.makeText(this@AuthActivity, "인증번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            })
        }
}