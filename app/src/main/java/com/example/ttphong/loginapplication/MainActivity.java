package com.example.ttphong.loginapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = SharedPreferencesHelper.loadSharedPreferences(this);

        if (isUserLogin()) {
            doLogin();
        } else {
            doLogout();
        }
    }

    private void doLogin() {
        Intent intent = new Intent(this, ListAlbumsActivity.class);
        startActivity(intent);
        finish();
    }

    private void doLogout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isUserLogin() {
        return mUser.getStatus();
    }
}
