package com.example.ttphong.loginapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttphong on 7/27/2016.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    // log tag
    private static final String TAG = "SQLite";
    // database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AlbumManager";
    // table album
    private static final String TABLE_ALBUM = "Album";
    private static final String COLUMN_ALBUM_ID = "_id";
    private static final String COLUMN_ALBUM_NAME = "name";
    // table photo
    private static final String TABLE_PHOTO = "Photo";
    private static final String COLUMN_PHOTO_ID = "_id";
    private static final String COLUMN_PHOTO_ALBUM_ID = "album_id";
    private static final String COLUMN_PHOTO_URL = "url";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");

        // creating album table
        String albumScript = "CREATE TABLE " + TABLE_ALBUM + "("
                + COLUMN_ALBUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + COLUMN_ALBUM_NAME + " TEXT)";
        sqLiteDatabase.execSQL(albumScript);

        // creating photo table
        String photoScript = "CREATE TABLE " + TABLE_PHOTO + "("
                + COLUMN_PHOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + COLUMN_PHOTO_URL + " TEXT, "
                + COLUMN_PHOTO_ALBUM_ID + " INTEGER NOT NULL CONSTRAINT " + COLUMN_PHOTO_ALBUM_ID
                + " REFERENCES " + TABLE_ALBUM + "(" + COLUMN_ALBUM_ID + ")" + " ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(photoScript);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        // drop existed table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ALBUM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTO);
        // recreating table
        onCreate(sqLiteDatabase);
    }

    // create default 2 albums
    public void createDefaultAlbumsIfNeed() {
        int count = this.getAlbumsCount();
        if(count == 0) {
            Album album1 = new Album("Album1");
            Album album2 = new Album("Album2");
            this.addAlbum(album1);
            this.addAlbum(album2);

            List<Album> albumList = this.getAllAlbums();
            Photo photo1 = new Photo("data/photo1.jpg");
            Photo photo2 = new Photo("data/photo2.jpg");
            this.addPhoto(albumList.get(0), photo1);
            this.addPhoto(albumList.get(0), photo2);
            this.addPhoto(albumList.get(1), photo2);
            this.addPhoto(albumList.get(1), photo1);
        }
    }

    public List<Album> getAllAlbums(){
        Log.i(TAG, "MyDatabaseHelper.getAllAlbums ... " );

        List<Album> albumList = new ArrayList<Album>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ALBUM;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Album album = new Album();
                album.setId(Integer.parseInt(cursor.getString(0)));
                album.setName(cursor.getString(1));
                albumList.add(album);
            } while (cursor.moveToNext());
        }
        return albumList;
    }

    public void addAlbum(Album album) {
        Log.i(TAG, "MyDatabaseHelper.addAlbum ... " + album.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ALBUM_NAME, album.getName());
        db.insert(TABLE_ALBUM, null, values);
        db.close();
    }

    public void deleteAlbum(Album album) {
        Log.i(TAG, "MyDatabaseHelper.deleteAlbum ... " + album.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALBUM, COLUMN_ALBUM_ID + " = ?",
                new String[] { String.valueOf(album.getId()) });
        db.close();
    }

    public int getAlbumsCount() {
        Log.i(TAG, "MyDatabaseHelper.getAlbumsCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_ALBUM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public List<Photo> getAllPhotos(Album album){
        Log.i(TAG, "MyDatabaseHelper.getAllPhotos ... " + album);

        List<Photo> photoList = new ArrayList<>();
        String selectQuery =
                "SELECT * FROM " + TABLE_PHOTO + " WHERE " + COLUMN_PHOTO_ALBUM_ID + " = " + album.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Photo photo = new Photo();
                photo.setId(Integer.parseInt(cursor.getString(0)));
                photo.setAlbumId(album.getId());
                photo.setUrl(cursor.getString(1));
                photoList.add(photo);
            } while (cursor.moveToNext());
        }
        return photoList;
    }

    public void addPhoto(Album album, Photo photo) {
        Log.i(TAG, "MyDatabaseHelper.addPhoto ... " + album.getName());
        Log.i(TAG, photo.getUrl());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHOTO_ALBUM_ID, album.getId());
        values.put(COLUMN_PHOTO_URL, photo.getUrl());
        db.insert(TABLE_PHOTO, null, values);
        db.close();
    }

    public void deletePhoto(Photo photo) {
        Log.i(TAG, "MyDatabaseHelper.deletePhoto ... " + photo.getUrl());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PHOTO, COLUMN_PHOTO_ID + " = ?",
                new String[] { String.valueOf(photo.getId()) });
        db.close();
    }
}
