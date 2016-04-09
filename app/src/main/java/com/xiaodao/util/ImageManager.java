package com.xiaodao.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.LruCache;

/**
 * Created by android on 2016/4/9.
 */
public class ImageManager {

    private LruCache<Integer,Bitmap> mLruCache;

    public ImageManager() {
        mLruCache = new LruCache<Integer,Bitmap>((int) (Runtime.getRuntime().maxMemory()/12)){
            @Override
            protected int sizeOf(Integer key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    public Bitmap getBitmap(int id, int bucket_id){
        Bitmap bitmap = mLruCache.get(bucket_id);
        if (bitmap!=null)return bitmap;

        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmap = MediaStore.Images.Thumbnails.getThumbnail(AppUtils.getContext().getContentResolver(),
                id, bucket_id, MediaStore.Images.Thumbnails.MICRO_KIND, options);
        mLruCache.put(bucket_id,bitmap);
        return bitmap;
    }
}
