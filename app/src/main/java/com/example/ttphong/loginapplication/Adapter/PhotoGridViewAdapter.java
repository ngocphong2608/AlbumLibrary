package com.example.ttphong.loginapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.ttphong.loginapplication.BitmapHelper;
import com.example.ttphong.loginapplication.DTO.Photo;
import com.example.ttphong.loginapplication.R;

import java.util.ArrayList;

/**
 * Created by ttphong on 7/28/2016.
 */
public class PhotoGridViewAdapter extends ArrayAdapter{
    private Context mContext;
    private int mLayoutResourceId;
    private ArrayList mData = new ArrayList();
    private Bitmap mDefaultPhoto;

    public PhotoGridViewAdapter(Context context, int resource, ArrayList data) {
        super(context, resource, data);

        this.mLayoutResourceId = resource;
        this.mContext = context;
        this.mData = data;
        // load default photo
        loadDefaultThumbnail();
    }

    private void loadDefaultThumbnail() {
        mDefaultPhoto = BitmapHelper.decodeSampledBitmapFromResource(
                mContext.getResources(), R.drawable.ic_photo, 100, 100);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageView holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            holder = (ImageView) row.findViewById(R.id.image_photo);
            row.setTag(holder);
        } else {
            holder = (ImageView) row.getTag();
        }

        Photo photo = (Photo) mData.get(position);
        DecodeImageTask imageTask = new DecodeImageTask(holder);
        imageTask.execute(photo.getUrl());
        return row;
    }

    private class DecodeImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DecodeImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String path = params[0];
            Bitmap bm = BitmapHelper.decodeSampledBitmapFromFile(path, 100, 100);
            return bm;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                bmImage.setImageBitmap(result);
            } else {
                bmImage.setImageBitmap(mDefaultPhoto);
            }
        }
    }
}
