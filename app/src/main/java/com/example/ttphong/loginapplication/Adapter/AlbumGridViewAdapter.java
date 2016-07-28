package com.example.ttphong.loginapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ttphong.loginapplication.AlbumImageItem;
import com.example.ttphong.loginapplication.R;

import java.util.ArrayList;

/**
 * Created by ttphong on 7/28/2016.
 */
public class AlbumGridViewAdapter extends ArrayAdapter {
    private Context mContext;
    private int mLayoutResourceId;
    private ArrayList mData = new ArrayList();

    public AlbumGridViewAdapter(Context context, int resource, ArrayList data) {
        super(context, resource, data);
        this.mLayoutResourceId = resource;
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text_album);
            holder.image = (ImageView) row.findViewById(R.id.image_album);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        AlbumImageItem item = (AlbumImageItem)mData.get(position);
        holder.imageTitle.setText(item.getTitle());
        holder.image.setImageBitmap(item.getImage());
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}