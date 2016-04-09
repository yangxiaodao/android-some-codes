package com.xiaodao.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by android on 2016/4/8.
 */
public class ImageData implements Serializable,Comparable<ImageData> {

    public String imagePath;
    public int imageId;
    public int imagePartentId;
    public boolean isSelect;

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

    @Override
    public int compareTo(ImageData another) {
        return this.imagePath.compareTo(another.imagePath);
    }


    public static class ImageFileItem implements Serializable{
        public String imagePath;
        public Bitmap fileBitmap;
        public int imageCount;
        public int imagePartentId;

        public ImageFileItem(String imagePath, Bitmap fileBitmap, int imagePartentId) {
            this.imagePath = imagePath;
            this.fileBitmap = fileBitmap;
            this.imagePartentId = imagePartentId;
        }
    }
}
