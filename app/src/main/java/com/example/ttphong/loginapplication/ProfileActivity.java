package com.example.ttphong.loginapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView tv_profileDetail;
    private Button btn_logout;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // load user
        mUser = SharedPreferencesHelper.loadSharedPreferences(this);
        // set user info
        tv_profileDetail = (TextView)findViewById(R.id.tv_profile);
        tv_profileDetail.setText(String.format(getString(R.string.title_profile_detail), mUser.getUserName()));
        // onclick logout
        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogoutClicked();
            }
        });
    }

    private void onLogoutClicked() {
        updateUserStatus(false);
        SharedPreferencesHelper.updateSharedPreferences(this, mUser);
        doLogout();
    }

    private void doLogout() {
        Intent intent  = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateUserStatus(boolean status) {
        mUser.setStatus(status);
    }
}
