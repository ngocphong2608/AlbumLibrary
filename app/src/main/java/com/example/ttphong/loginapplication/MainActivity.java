package com.example.ttphong.loginapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_USERNAME = "PREF_USERNAME";
    private static final String PREF_ENCRYPTED_PASSWORD = "PREF_ENCRYPTED_PASSWORD";
    private static final String PREF_STATUS = "PREF_STATUS";
    private final String DEFAULT_USERNAME = "thanhphong";
    private final String DEFAULT_PASSWORD = "123456";
    private final boolean DEFAULT_STATUS = false;
    private String mUserName = DEFAULT_USERNAME;
    private String mPassWord = DEFAULT_PASSWORD;
    private boolean mStatus = DEFAULT_STATUS;
    private TextView tv_profileDetail;
    private EditText edt_userName;
    private EditText edt_passWord;
    private Button btn_logout;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSharedPreferences();

        if (isUserLogin()) {
            doLogin();
        } else {
            doLogout();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        updateSharedPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void doLogin() {
        setContentView(R.layout.activity_main_profile);
        // display mUserName
        tv_profileDetail = (TextView)findViewById(R.id.tv_profile);
        tv_profileDetail.setText(getString(R.string.title_profile_detail) + " " + mUserName);
        // onclick logout
        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogoutClicked();
            }
        });
    }

    private void doLogout() {
        setContentView(R.layout.activity_main_login);
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

    private void loadSharedPreferences(){
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        this.mUserName = pref.getString(PREF_USERNAME, this.mUserName);
        String encryptedPass = pref.getString(PREF_ENCRYPTED_PASSWORD, null);
        this.mStatus = pref.getBoolean(PREF_STATUS, this.mStatus);

        if (encryptedPass != null) {
            this.mPassWord = decryptPassWord(encryptedPass);
        }
    }

    private void updateSharedPreferences(){
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(PREF_USERNAME, this.mUserName);
        edit.putString(PREF_ENCRYPTED_PASSWORD, encryptPassWord(this.mPassWord));
        edit.putBoolean(PREF_STATUS, this.mStatus);
        edit.commit();
    }

    private String encryptPassWord(String passWord) {
        // dummy
        return passWord;
    }

    private String decryptPassWord(String encryptedPass){
        // dummy
        return encryptedPass;
    }

    private boolean isUserLogin() {
        return this.mStatus;
    }

    public void onLoginClicked() {
        String userName = edt_userName.getText().toString().toLowerCase();
        String passWord = edt_passWord.getText().toString();

        if (userName.contentEquals("") || passWord.contentEquals("")) {
            Toast.makeText(this, getString(R.string.error_username_password_blank), Toast.LENGTH_SHORT).show();
        } else if (matchPassWord(userName, passWord)) {
            // update user status
            updateUserStatus(true);
            doLogin();
        } else {
            Toast.makeText(this, getString(R.string.error_sign_in_fail), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUserStatus(boolean status) {
        this.mStatus = status;
    }

    private boolean matchPassWord(String userName, String passWord) {
        return this.mUserName.contentEquals(userName) && this.mPassWord.contentEquals(passWord);
    }

    public void onLogoutClicked() {
        // update user status
        updateUserStatus(false);
        doLogout();
    }
}
