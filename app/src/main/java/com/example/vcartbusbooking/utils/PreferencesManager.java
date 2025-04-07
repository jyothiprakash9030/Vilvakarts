package com.example.vcartbusbooking.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class PreferencesManager {

    private static final String PREF_NAME = "secure_prefs";
    private static final String USERNAME_KEY = "username";
    private static final String TOKEN_KEY = "token";
    private static final String STORE_ID_KEY = "storeId";
    private static final String STORE_CODE_KEY = "storeCode";
    private static final String STORE_NAME_KEY = "storeName";
    private static final String ROLE_ID_KEY = "roleId";
    private static final String ROLE_NAME_KEY = "roleName";

    private SharedPreferences sharedPreferences;

    public PreferencesManager(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    PREF_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    // Save user data
    public void saveUser(User user) {
        sharedPreferences.edit()
                .putString(USERNAME_KEY, user.getUsername())
                .putString(TOKEN_KEY, user.getToken())
                .putInt(STORE_ID_KEY, user.getStoreId())
                .putString(STORE_CODE_KEY, user.getStoreCode())
                .putString(STORE_NAME_KEY, user.getStoreName())
                .putInt(ROLE_ID_KEY, user.getRoleId())
                .putString(ROLE_NAME_KEY, user.getRoleName())
                .apply();
    }

    // Retrieve user data
    public User getUser() {
        String username = sharedPreferences.getString(USERNAME_KEY, null);
        String token = sharedPreferences.getString(TOKEN_KEY, null);
        int storeId = sharedPreferences.getInt(STORE_ID_KEY, -1);
        String storeCode = sharedPreferences.getString(STORE_CODE_KEY, null);
        String storeName = sharedPreferences.getString(STORE_NAME_KEY, null);
        int roleId = sharedPreferences.getInt(ROLE_ID_KEY, -1);
        String roleName = sharedPreferences.getString(ROLE_NAME_KEY, null);

        return new User(username, token, storeId, storeCode, storeName, roleId, roleName);
    }

    // Clear user data
    public void clearUser() {
        sharedPreferences.edit()
                .remove(USERNAME_KEY)
                .remove(TOKEN_KEY)
                .remove(STORE_ID_KEY)
                .remove(STORE_CODE_KEY)
                .remove(STORE_NAME_KEY)
                .remove(ROLE_ID_KEY)
                .remove(ROLE_NAME_KEY)
                .apply();
    }
}
