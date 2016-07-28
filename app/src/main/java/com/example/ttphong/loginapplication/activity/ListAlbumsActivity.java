package com.example.ttphong.loginapplication.activity;

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
import android.widget.Toast;

import com.example.ttphong.loginapplication.DTO.Album;
import com.example.ttphong.loginapplication.AlbumImageItem;
import com.example.ttphong.loginapplication.DTO.User;
import com.example.ttphong.loginapplication.MyDatabaseHelper;
import com.example.ttphong.loginapplication.R;
import com.example.ttphong.loginapplication.SharedPreferencesHelper;
import com.example.ttphong.loginapplication.adapter.AlbumGridViewAdapter;
import com.example.ttphong.loginapplication.dialog.NewAlbumDialog;

import java.util.ArrayList;
import java.util.List;

public class ListAlbumsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, NewAlbumDialog.OnDialogOkListener{
    private GridView mGridView;
    private AlbumGridViewAdapter mGridAdapter;
    private MyDatabaseHelper mHelper;
    private List<Album> mAlbumList;
    private Bitmap mDefaultBitmap;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        // get user from shared preferences
        mUser = SharedPreferencesHelper.loadSharedPreferences(this);
        // database
        mHelper = new MyDatabaseHelper(this);
        mHelper.createDefaultAlbumsIfNeed();
        // load default bitmap for album
        loadDefaultAlbumBitmap();
        // set gridview adapter
        mGridView = (GridView) findViewById(R.id.gv_album);
        mGridAdapter = new AlbumGridViewAdapter(this, R.layout.item_album, getData());
        mGridView.setAdapter(mGridAdapter);
        // gridview onclick item
        mGridView.setOnItemClickListener(this);
        // toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.tb_list_albums);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_albums, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            // update user status
            mUser.setStatus(false);
            SharedPreferencesHelper.updateSharedPreferences(this, mUser);
            // start LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.action_add_album) {
            NewAlbumDialog dlg = new NewAlbumDialog(this);
            dlg.show();
        } else if (id == R.id.action_delete_album) {
            Toast.makeText(this, "Method is not implemented yet", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateAlbumGridView() {
        mGridAdapter = new AlbumGridViewAdapter(this, R.layout.item_album, getData());
        mGridView.setAdapter(mGridAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(ListPhotosActivity.getIntent(this, mAlbumList.get(i)));
    }

    private void loadDefaultAlbumBitmap() {
        mDefaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_album);
    }

    private ArrayList getData() {
        mAlbumList = mHelper.getAllAlbums();
        ArrayList<AlbumImageItem> imageItems = new ArrayList<>();

        for (Album album : mAlbumList) {
            AlbumImageItem item = new AlbumImageItem(mDefaultBitmap, album.getName());
            imageItems.add(item);
        }
        return imageItems;
    }

    @Override
    public void OnDialogOkListener(String albumName) {
        Album album = new Album(albumName);
        mHelper.addAlbum(album);
        updateAlbumGridView();
    }
}
