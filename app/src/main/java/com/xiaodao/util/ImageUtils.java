package com.xiaodao.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.xiaodao.log.XLog;

/**
 * 处理图片
 * Created by air on 15/12/26.
 */
public class ImageUtils {

    /**
     * 打印Bitmap的width，height及size
     * @param bitmap
     */
    public static void logBitmapInfo(Bitmap bitmap){
        if (bitmap != null){
            XLog.i("w:" + bitmap.getWidth() + "\th:" + bitmap.getHeight() + "\t"
                    + bitmap.getConfig() + "\t" + bitmap.getByteCount() / 1024 + "k");
        }
    }

    /**
     * see {@link #logBitmapInfo(Bitmap)}
     * @param imageView
     */
    public static void logBitmapInfo(ImageView imageView){
        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            XLog.i("w:"+bitmap.getWidth()+"\th:"+bitmap.getHeight()+"\t"
                    +bitmap.getConfig()+"\t"+bitmap.getByteCount()/1024+"k");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
