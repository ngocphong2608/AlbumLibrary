package com.example.ttphong.loginapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ttphong on 7/28/2016.
 */
public class SharedPreferencesHelper {

    private static final String PREF_USERNAME = "PREF_USERNAME";
    private static final String PREF_PASSWORD_HASHING = "PREF_PASSWORD_HASHING";
    private static final String PREF_STATUS = "PREF_STATUS";
    private static final String PREF_FILE_NAME = "com.tma.ttphong.LOGIN_APPLICATION";

    public static User loadSharedPreferences(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

        User user = new User();
        user.setUserName(pref.getString(PREF_USERNAME, User.DEFAULT_USERNAME));
        user.setPassWordHashing(pref.getString(PREF_PASSWORD_HASHING, null));
        user.setStatus(pref.getBoolean(PREF_STATUS, User.DEFAULT_STATUS));
        if (user.getPassWordHashing() == null){
            user.setPassWordHashing(MD5Hashing.computeHash(User.DEFAULT_PASSWORD));
        }
        return user;
    }

    public static void updateSharedPreferences(Context context, User user){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(PREF_USERNAME, user.getUserName());
        edit.putString(PREF_PASSWORD_HASHING, user.getPassWordHashing());
        edit.putBoolean(PREF_STATUS, user.getStatus());
        edit.commit();
    }
}
