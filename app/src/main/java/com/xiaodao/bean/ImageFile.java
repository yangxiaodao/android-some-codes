package com.xiaodao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 2016/4/9.
 */
public class ImageFile implements Serializable {

    public List<ImageData> list;

    public ImageFile(List<ImageData> list) {
        this.list = list;
    }
}
