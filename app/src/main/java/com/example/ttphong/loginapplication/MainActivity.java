package com.example.ttphong.loginapplication;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private final String MY_RESOURCE_FILE = "/my_resource.dat";
    private String userName = "thanhphong";
    private String passWord = "123456";
    private boolean status = false;
    private TextView tv_profile_detail;
    private EditText edt_userName;
    private EditText edt_passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadResource();

        if (isUserLogin()) {
            setContentView(R.layout.activity_profile);
            // display userName
            tv_profile_detail = (TextView)findViewById(R.id.tv_profile);
            tv_profile_detail.setText(getString(R.string.title_profile_detail) + " " + userName);
        } else {
            setContentView(R.layout.activity_login);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        saveResource();
    }

    private void saveResource() {
        File file = getFilesDir();
        String path = file.getAbsolutePath();
        File resourceFile = new File(path + MY_RESOURCE_FILE);

        try {
            ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(resourceFile));
            MyResource res = new MyResource();
            res.userName = userName;
            res.passWord = passWord;
            res.status = status;
            obj.writeObject(res);
            obj.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        saveResource();
    }

    private void loadResource() {
        File file = getFilesDir();
        String path = file.getAbsolutePath();
        File resourceFile = new File(path + MY_RESOURCE_FILE);

        if (resourceFile.exists()) {
            try {
                ObjectInputStream obj = new ObjectInputStream(new FileInputStream(resourceFile));
                MyResource res = (MyResource)obj.readObject();
                userName = res.userName;
                passWord = res.passWord;
                status = res.status;
                obj.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUserLogin() {
        return status;
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

    public void onLogin(View view) {
        edt_userName = (EditText)findViewById(R.id.edt_username);
        edt_passWord = (EditText)findViewById(R.id.edt_password);

        String userName = edt_userName.getText().toString().toLowerCase();
        String passWord = edt_passWord.getText().toString();

        if (userName.contentEquals("") || passWord.contentEquals("")) {
            Toast.makeText(this, getString(R.string.error_username_password_blank), Toast.LENGTH_SHORT).show();
        } else if (matchPassWord(userName, passWord)) {
            // update user status
            updateUserStatus(true);
            setContentView(R.layout.activity_profile);
            // display userName
            tv_profile_detail = (TextView)findViewById(R.id.tv_profile);
            tv_profile_detail.setText(getString(R.string.title_profile_detail) + " " + userName);
        } else {
            Toast.makeText(this, getString(R.string.error_sign_in_fail), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUserStatus(boolean status) {
        this.status = status;
    }

    private boolean matchPassWord(String userName, String passWord) {
        return this.userName.contentEquals(userName) && this.passWord.contentEquals(passWord);
    }

    public void onLogout(View view) {
        // update user status
        updateUserStatus(false);
        setContentView(R.layout.activity_login);
    }
}
