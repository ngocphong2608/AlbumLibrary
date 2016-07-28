package com.example.ttphong.loginapplication;

import android.graphics.Bitmap;

/**
 * Created by ttphong on 7/28/2016.
 */
public class AlbumImageItem {
    private Bitmap image;
    private String title;

    public AlbumImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
