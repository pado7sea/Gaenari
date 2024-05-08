package com.example.gaenari.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class PreferencesUtil {
    private static SharedPreferences instance = null;
    private static final String FILE_NAME = "encrypted_prefs";

    private PreferencesUtil() {
    }

    public static synchronized SharedPreferences getEncryptedSharedPreferences(Context context) {
        if (instance == null) {
            try {
                MasterKey masterKey = new MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                        .build();

                instance = EncryptedSharedPreferences.create(
                        context,
                        FILE_NAME,
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );
            } catch (GeneralSecurityException | IOException e) {
                // Handle the exceptions properly in your real application
                throw new RuntimeException("Could not create EncryptedSharedPreferences", e);
            }
        }
        return instance;
    }
}
