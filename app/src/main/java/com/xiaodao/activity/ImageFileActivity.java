package com.xiaodao.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.xiaodao.R;
import com.xiaodao.base.BaseActivity;
import com.xiaodao.base.DefaultAdapter;
import com.xiaodao.bean.ImageData;
import com.xiaodao.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 2016/4/8.
 */
public class ImageFileActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.list)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_file);
        ButterKnife.bind(this);
        initToolbar(mToolbar, R.string.image_file);

        List<ImageData> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new ImageData("/path", 0, 0));
        }
        ImageAdapter imageAdapter = new ImageAdapter(list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        layoutManager.offsetChildrenVertical(100);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(imageAdapter);
    }

    private class ImageAdapter extends DefaultAdapter<ImageData, ImageViewHolder> {

        public ImageAdapter(List<ImageData> list) {
            super(list);
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageViewHolder(View.inflate(AppUtils.getContext(),
                    R.layout.item_image_select, null));
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            View itemView = holder.itemView;
            int w = getResources().getDisplayMetrics().widthPixels;
            int image_w = (w - 36 * 3) / 3;
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                    image_w, image_w
            );
            layoutParams.topMargin = 36;
            itemView.setLayoutParams(layoutParams);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }
}
