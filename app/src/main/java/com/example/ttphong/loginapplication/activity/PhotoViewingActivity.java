package com.example.ttphong.loginapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.ttphong.loginapplication.BitmapHelper;
import com.example.ttphong.loginapplication.DTO.Photo;
import com.example.ttphong.loginapplication.DTO.User;
import com.example.ttphong.loginapplication.R;
import com.example.ttphong.loginapplication.SharedPreferencesHelper;

import java.io.File;

public class PhotoViewingActivity extends AppCompatActivity {

    private static final String EXTRA_PHOTO = "EXTRA_PHOTO";
    private User mUser;
    private Photo mPhoto;
    private ImageView image_PhotoViewing;
    private Bitmap mDefaultPhoto;

    public static Intent getIntent(Context context, Photo photo) {
        Intent intent =  new Intent(context, PhotoViewingActivity.class);
        intent.putExtra(EXTRA_PHOTO, photo);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewing);

        // get intent and photo from intent extra
        Intent intent = getIntent();
        mPhoto = (Photo)intent.getSerializableExtra(EXTRA_PHOTO);
        // get user from shared preferences
        mUser = SharedPreferencesHelper.loadSharedPreferences(this);
        // load default photo
        mDefaultPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.ic_photo);
        // display photo
        image_PhotoViewing = (ImageView)findViewById(R.id.image_photo_viewing);
        image_PhotoViewing.setImageBitmap(loadFullBitmap());
        // toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.tb_photo_viewing);
        setSupportActionBar(toolbar);
    }

    private Bitmap loadFullBitmap() {
        File file = new File(mPhoto.getUrl());
        if (file.exists()) {
            return BitmapHelper.loadFullBitmap(mPhoto.getUrl());
        } else {
            return mDefaultPhoto;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            // update user status
            mUser.setStatus(false);
            SharedPreferencesHelper.updateSharedPreferences(this, mUser);
            // start LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
