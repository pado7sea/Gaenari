package com.example.gaenari.activity.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gaenari.R;
import com.example.gaenari.util.AccessToken;
import com.example.gaenari.util.PreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private final List<String> permissions = new ArrayList<>();
    private boolean shouldShowSettings = false;
    private ActivityResultLauncher<Intent> watchPermissionLauncher;

    /* Activity 생성 시 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        prefs = PreferencesUtil.getEncryptedSharedPreferences(getApplicationContext());

        /* 권한 설정 확인 */
        requestPermissionsIfNeed();

        applySystemBarsPadding();
    }

    /* 설정 창에서 복귀 시 */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /* ====권한 정보 확인==== */

    /**
     * 필요한 권한 정보 추가
     */
    private void requestPermissionsIfNeed() {
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACTIVITY_RECOGNITION);
        permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        permissions.add(Manifest.permission.VIBRATE);
        permissions.add(Manifest.permission.BODY_SENSORS);

        requestPermission(0);
    }


    /**
     * 권한 별로 설정 여부 확인
     */
    private void requestPermission(int permissionIdx) {
        if (permissionIdx < permissions.size()) {
            String permission = permissions.get(permissionIdx);
            Log.d("Check Main Activity", "Request Permission : " + permission);
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, permissionIdx);
            } else {
                requestPermission(++permissionIdx);
            }
        } else {
            /* AccessToken 유무에 따라 Activity 이동 */
            if (!hasAccessToken()) {
                redirectAuthActivity();
            } else {
                redirectHomeActivity();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 권한이 부여된 경우 다음 권한 확인으로 이동
            requestPermission(requestCode + 1);
        } else {
            // 권한이 거부된 경우 알림을 표시하거나 처리
            showSettingsAlert();
        }
    }

    /* 설정 창 이동 부분 수정 필요 */

    /**
     * 설정 창 이동 알림 표시
     */
    private void showSettingsAlert() {
        new AlertDialog.Builder(this)
                .setTitle("권한 설정 필요")
                .setMessage("개나리의 모든 기능을 사용하기 위해서는 설정에서 권한(위치, 신체 활동, 센서)을 승인해야 합니다.")
                .setPositiveButton("설정", (dialog, which) -> goToSettings())
                .setNegativeButton("취소", (dialog, which) -> finish())
                .create().show();
    }

    /**
     * 워치 설정 창으로 이동
     */
    private void goToSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        watchPermissionLauncher.launch(intent);
    }

    /* ====AccessToken 판단==== */

    /**
     * AccessToken 유무 판단
     *
     * @return boolean
     */
    private boolean hasAccessToken() {
        String token = AccessToken.getInstance().getAccessToken();
        String subToken = prefs.getString("accessToken", null);
        Log.d("Check Main Activity", "AccessToken : " + token);
        if (token != null)
            return true;
        else if (subToken != null) {
            Log.d("Check Main Activity", "SubAccessToken : " + subToken);
            AccessToken.getInstance().setAccessToken(subToken);
            return true;
        }
        return false;
    }

    /* ====화면 전환==== */

    /**
     * 사용자 계정 연동 화면 이동
     */
    private void redirectAuthActivity() {
        Intent intent = new Intent(MainActivity.this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 홈 화면 이동
     */
    private void redirectHomeActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void applySystemBarsPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}