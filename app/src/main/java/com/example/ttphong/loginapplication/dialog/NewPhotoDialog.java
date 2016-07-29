package com.example.ttphong.loginapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.example.ttphong.loginapplication.R;
import com.example.ttphong.loginapplication.activity.ListPhotosActivity;

/**
 * Created by ttphong on 7/28/2016.
 */
public class NewPhotoDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private NewPhotoDialogListener mCallback;
    Button btn_gallery;
    Button btn_camera;

    @Override
    public void onClick(View view) {
        if (view.getId() == btn_gallery.getId()) {
            mCallback.OnGalleryClicked();
            this.dismiss();
        } else if (view.getId() == btn_camera.getId()) {
            mCallback.OnCameraClicked();
            this.dismiss();
        }
    }

    public interface NewPhotoDialogListener {
        void OnGalleryClicked();
        void OnCameraClicked();
    }

    public NewPhotoDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_new_photo);
        setTitle(context.getString(R.string.title_photo_dialog));

        mContext = context;
        mCallback = (ListPhotosActivity)mContext;
        btn_gallery = (Button)findViewById(R.id.btn_new_photo_from_gallery);
        btn_camera = (Button)findViewById(R.id.btn_new_photo_from_camera);

        btn_camera.setOnClickListener(this);
        btn_gallery.setOnClickListener(this);
    }
}
