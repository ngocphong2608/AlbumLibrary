package com.example.ttphong.loginapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ttphong.loginapplication.BitmapHelper;
import com.example.ttphong.loginapplication.DTO.Album;
import com.example.ttphong.loginapplication.DTO.User;
import com.example.ttphong.loginapplication.MyDatabaseHelper;
import com.example.ttphong.loginapplication.DTO.Photo;
import com.example.ttphong.loginapplication.R;
import com.example.ttphong.loginapplication.SharedPreferencesHelper;
import com.example.ttphong.loginapplication.adapter.PhotoGridViewAdapter;
import com.example.ttphong.loginapplication.dialog.NewPhotoDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListPhotosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, NewPhotoDialog.NewPhotoDialogListener{

    private static final String EXTRA_ALBUM = "EXTRA_ALBUM";
    private static final int REQUEST_CAMERA = 1000;
    private static final int REQUEST_GALLERY = 1001;
    private Album mAlbum;
    private GridView gv_listPhotos;
    private PhotoGridViewAdapter mGridViewAdapter;
    private MyDatabaseHelper mDbHelper;
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
        // init database
        mDbHelper = new MyDatabaseHelper(this);
        mPhotoList = mDbHelper.getAllPhotos(mAlbum);
        // set toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.tb_list_photos);
        setSupportActionBar(toolbar);
        // init gridview
        gv_listPhotos = (GridView)findViewById(R.id.gv_photo);
        mGridViewAdapter = new PhotoGridViewAdapter(this, R.layout.item_photo, new ArrayList(mPhotoList));
        gv_listPhotos.setAdapter(mGridViewAdapter);
        gv_listPhotos.setOnItemClickListener(this);
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
        } else if (id == R.id.action_add_photo) {
            NewPhotoDialog dlg = new NewPhotoDialog(this);
            dlg.show();
        } else if (id == R.id.action_delete_photo) {
            Toast.makeText(this, "Method is not implemented yet", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(PhotoViewingActivity.getIntent(this, mPhotoList.get(i)));
    }

    @Override
    public void OnGalleryClicked() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_GALLERY);
    }

    @Override
    public void OnCameraClicked() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCapturePhotoResult(data);
        }
    }

    private void onCapturePhotoResult(Intent data) {
        Bitmap bm = (Bitmap) data.getExtras().get("data");
        String path = Environment.getExternalStorageDirectory().toString() + "/" +
                System.currentTimeMillis() + ".jpg";
        // store database
        Photo photo = new Photo(path);
        mDbHelper.addPhoto(mAlbum, photo);
        // store image
        BitmapHelper.saveBitmap(bm, path);
        // refresh gridView
        refreshPhotoGridView();
    }

    private void refreshPhotoGridView() {
        mPhotoList = mDbHelper.getAllPhotos(mAlbum);
        mGridViewAdapter = new PhotoGridViewAdapter(this, R.layout.item_photo, new ArrayList(mPhotoList));
        gv_listPhotos.setAdapter(mGridViewAdapter);
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                String path = Environment.getExternalStorageDirectory().toString() + "/" +
                        System.currentTimeMillis() + ".jpg";
                // store database
                Photo photo = new Photo(path);
                mDbHelper.addPhoto(mAlbum, photo);
                // store image
                BitmapHelper.saveBitmap(bm, path);
                // refresh gridview
                refreshPhotoGridView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
