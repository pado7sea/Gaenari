package com.example.gaenari.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.gaenari.dto.response.AuthResponseDto;
import com.example.gaenari.dto.response.MyPetResponseDto;

public class MemberInfo {

    private SharedPreferences prefs;

    public void getMemberInfo(Context context, AuthResponseDto responseDto){
        Log.i("Check Member Info", "Save MemberInfo : " + responseDto.toString());

        prefs = PreferencesUtil.getEncryptedSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();

        edit.putLong("memberId", responseDto.getMemberId());
        edit.putString("accountId", responseDto.getAccountId());
        edit.putString("nickname", responseDto.getNickname());
        edit.putString("gender", responseDto.getGender());
        edit.putInt("height", responseDto.getHeight());
        edit.putInt("weight", responseDto.getWeight());
        edit.putLong("petId", responseDto.getPetId());
        edit.putString("petName", responseDto.getPetName());
        edit.apply();

        Log.d("Check", "Complete Save MemberInfo");
    }
}
