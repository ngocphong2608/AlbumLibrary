package com.example.ttphong.loginapplication.dialog;

import android.app.Dialog;
import android.content.Context;

import com.example.ttphong.loginapplication.R;

/**
 * Created by ttphong on 7/28/2016.
 */
public class NewPhotoDialog extends Dialog {

    Context mContext;

    public interface OnPhotoDialogResponse {

    }

    public NewPhotoDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_new_photo);
        setTitle(context.getString(R.string.title_photo_dialog));

        mContext = context;
    }
}
