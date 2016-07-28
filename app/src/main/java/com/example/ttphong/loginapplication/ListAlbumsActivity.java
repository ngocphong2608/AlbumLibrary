package com.example.ttphong.loginapplication;

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

import com.example.ttphong.loginapplication.Adapter.AlbumGridViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListAlbumsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private GridView mGridView;
    private AlbumGridViewAdapter mGridAdapter;
    private MyDatabaseHelper mHelper;
    private List<Album> mAlbumList;
    private Bitmap mDefaultBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        // database
        mHelper = new MyDatabaseHelper(this);
        mHelper.createDefaultAlbumsIfNeed();
        mAlbumList = mHelper.getAllAlbums();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(ListPhotosActivity.getIntent(this, mAlbumList.get(i)));
    }

    private void loadDefaultAlbumBitmap() {
        mDefaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_album);
    }

    private ArrayList getData() {
        ArrayList<AlbumImageItem> imageItems = new ArrayList<>();

        for (Album album : mAlbumList) {
            AlbumImageItem item = new AlbumImageItem(mDefaultBitmap, album.getName());
            imageItems.add(item);
        }
        return imageItems;
    }
}
