package com.example.ttphong.loginapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_userName;
    private EditText edt_passWord;
    private Button btn_login;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get user from sharedpreferences
        mUser = SharedPreferencesHelper.loadSharedPreferences(this);
        // edit text
        edt_userName = (EditText)findViewById(R.id.edt_username);
        edt_passWord = (EditText)findViewById(R.id.edt_password);
        // onclick login
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginClicked();
            }
        });
    }

    private void onLoginClicked() {
        String userName = edt_userName.getText().toString().toLowerCase();
        String passWord = edt_passWord.getText().toString();

        if (userName.contentEquals("") || passWord.contentEquals("")) {
            Toast.makeText(this, getString(R.string.error_username_password_blank), Toast.LENGTH_SHORT).show();
        } else if (matchPassWord(userName, passWord)) {
            // update user status
            updateUserStatus(true);
            SharedPreferencesHelper.updateSharedPreferences(this, mUser);
            doLogin();
        } else {
            Toast.makeText(this, getString(R.string.error_sign_in_fail), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUserStatus(boolean status) {
        mUser.setStatus(status);
    }

    private void doLogin() {
        Intent intent = new Intent(this, ListAlbumsActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean matchPassWord(String userName, String passWord) {
        String passHash = MD5Hashing.computeHash(passWord);
        return mUser.getUserName().contentEquals(userName)
                && mUser.getPassWordHashing().contentEquals(passHash);
    }

}
