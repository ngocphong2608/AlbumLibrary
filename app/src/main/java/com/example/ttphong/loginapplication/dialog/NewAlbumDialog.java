package com.example.ttphong.loginapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ttphong.loginapplication.R;
import com.example.ttphong.loginapplication.activity.ListAlbumsActivity;

/**
 * Created by ttphong on 7/28/2016.
 */
public class NewAlbumDialog extends Dialog implements View.OnClickListener {

    private Button btn_ok;
    private Button btn_cancel;
    private EditText edt_albumName;
    private Context mContext;
    private OnDialogOkListener mCallBack;

    public interface OnDialogOkListener {
        void OnOkClicked(String albumName);
    }

    public NewAlbumDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_new_album);
        setTitle(mContext.getString(R.string.title_album_dialog));

        mContext = context;
        mCallBack = (ListAlbumsActivity)context;
        btn_ok  = (Button)findViewById(R.id.btn_ok);
        btn_cancel  = (Button)findViewById(R.id.btn_cancel);
        edt_albumName = (EditText)findViewById(R.id.edt_album_name);
        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btn_ok.getId()) {
            String text = edt_albumName.getText().toString();
            if (text.compareTo("") != 0) {
                mCallBack.OnOkClicked(text);
                this.dismiss();
            } else {
                Toast.makeText(mContext, mContext.getString(R.string.error_album_name_blank), Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == btn_cancel.getId()) {
            this.dismiss();
        }
    }
}
