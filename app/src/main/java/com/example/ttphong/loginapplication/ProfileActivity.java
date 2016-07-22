package com.example.ttphong.loginapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "EXTRA_USERNAME";
    private TextView tv_profileDetail;
    private Button btn_logout;

    public static Intent getStartIntent(Context context, String userName) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_USERNAME, userName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);
        tv_profileDetail = (TextView)findViewById(R.id.tv_profile);
        btn_logout = (Button)findViewById(R.id.btn_logout);

        Intent intent = getIntent();
        String userName = intent.getStringExtra(EXTRA_USERNAME);
        tv_profileDetail.setText(getString(R.string.title_profile_detail) + " " + userName);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
