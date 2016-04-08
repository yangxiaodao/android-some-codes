package com.xiaodao.bean;

import android.graphics.Bitmap;

/**
 * Created by android on 2016/4/8.
 */
public class ImageData {

    public String imagePath;
    public int imageId;
    public int imagePartentId;

    public ImageData(String imagePath, int imageId, int imagePartentId) {
        this.imagePath = imagePath;
        this.imageId = imageId;
        this.imagePartentId = imagePartentId;
    }

    @Override
    public String toString() {
        return "ImageData{" +
                "imagePath='" + imagePath + '\'' +
                ", imageId=" + imageId +
                ", imagePartentId=" + imagePartentId +
                '}';
    }

    public static class ImageFileItem {
        public String imagePath;
        public Bitmap fileBitmap;
        public int imageCount;

        public ImageFileItem(String imagePath, Bitmap fileBitmap) {
            this.imagePath = imagePath;
            this.fileBitmap = fileBitmap;
        }
    }
}
