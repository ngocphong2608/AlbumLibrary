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
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.ttphong.loginapplication.DTO.Album;
import com.example.ttphong.loginapplication.DTO.User;
import com.example.ttphong.loginapplication.MyDatabaseHelper;
import com.example.ttphong.loginapplication.DTO.Photo;
import com.example.ttphong.loginapplication.R;
import com.example.ttphong.loginapplication.SharedPreferencesHelper;
import com.example.ttphong.loginapplication.adapter.PhotoGridViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListPhotosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String EXTRA_ALBUM = "EXTRA_ALBUM";
    private Album mAlbum;
    private GridView gv_listPhotos;
    private PhotoGridViewAdapter mGridViewAdapter;
    private Bitmap mDefaultPhoto;
    private MyDatabaseHelper mHelper;
    private List<Photo> mPhotoList;
    private User mUser;

    public static Intent getIntent(Context context, Album album) {
        Intent intent = new Intent(context, ListPhotosActivity.class);
        intent.putExtra(EXTRA_ALBUM, album);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        // get album from intent
        Intent intent = getIntent();
        mAlbum  = (Album)intent.getSerializableExtra(EXTRA_ALBUM);
        // get user from shared preferences
        mUser = SharedPreferencesHelper.loadSharedPreferences(this);
        // load default photo
        loadDefaultPhoto();
        // init database
        mHelper = new MyDatabaseHelper(this);
        mPhotoList = mHelper.getAllPhotos(mAlbum);
        // set toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.tb_list_photos);
        setSupportActionBar(toolbar);
        // init gridview
        gv_listPhotos = (GridView)findViewById(R.id.gv_photo);
        mGridViewAdapter = new PhotoGridViewAdapter(this, R.layout.item_photo, getData());
        gv_listPhotos.setAdapter(mGridViewAdapter);
        gv_listPhotos.setOnItemClickListener(this);
    }

    private void loadDefaultPhoto() {
        mDefaultPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.ic_photo);
    }

    private ArrayList getData() {
        ArrayList<Bitmap> arrayList = new ArrayList<>();
        for (Photo photo : mPhotoList) {
            arrayList.add(mDefaultPhoto);
        }
        return arrayList;
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
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(PhotoViewingActivity.getIntent(this, mPhotoList.get(i)));
    }
}
