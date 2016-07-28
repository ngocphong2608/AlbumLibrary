package com.example.ttphong.loginapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.ttphong.loginapplication.R;

import java.util.ArrayList;

/**
 * Created by ttphong on 7/28/2016.
 */
public class PhotoGridViewAdapter extends ArrayAdapter{
    private Context mContext;
    private int mLayoutResourceId;
    private ArrayList mData = new ArrayList();

    public PhotoGridViewAdapter(Context context, int resource, ArrayList data) {
        super(context, resource, data);
        this.mLayoutResourceId = resource;
        this.mContext = context;
        this.mData = data;
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
        Bitmap item = (Bitmap) mData.get(position);
        holder.setImageBitmap(item);
        return row;
    }
}
