package com.example.ttphong.loginapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ttphong on 7/29/2016.
 */
public class BitmapHelper {
    public static void saveBitmap(Bitmap bm, String path) {
        File file = new File(path);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        FileOutputStream fo;

        try {
            boolean ok = file.createNewFile();
            fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadBitmap(String path) {
        return BitmapFactory.decodeFile(path);
    }
}
