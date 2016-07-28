package com.example.ttphong.loginapplication;

import java.io.Serializable;

/**
 * Created by ttphong on 7/27/2016.
 */
public class Photo implements Serializable {
    private int mId;
    private int mAlbumId;
    private String mUrl;

    public Photo(){

    }

    public Photo(String url) {
        this.mUrl = url;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(int albumId) {
        mAlbumId = albumId;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString(){
        return this.mUrl;
    }
}
