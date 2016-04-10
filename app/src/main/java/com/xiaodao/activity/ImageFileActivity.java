package com.xiaodao.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaodao.R;
import com.xiaodao.base.BaseActivity;
import com.xiaodao.base.DefaultAdapter;
import com.xiaodao.bean.ImageData;
import com.xiaodao.bean.ImageFile;
import com.xiaodao.util.AppUtils;
import com.xiaodao.util.ImageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 2016/4/8.
 */
public class ImageFileActivity extends BaseActivity {

    // TODO: 2016/4/9 目前采用Glide加载图片（暂未发现卡顿，只缓存了当前页面） 

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.list)
    RecyclerView mRecyclerView;

    private List<ImageData> list;

    private List<ImageData> selectImages = new ArrayList<>();

    private ImageManager mImageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_file);
        ButterKnife.bind(this);
        initToolbar(mToolbar, R.string.image_file);

        ImageFile imageFile = (ImageFile) getIntent().getSerializableExtra("imageFile");
        list = imageFile.list;
        ImageAdapter imageAdapter = new ImageAdapter(list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        layoutManager.offsetChildrenVertical(100);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(imageAdapter);
        mImageManager = new ImageManager();

        mRecyclerView.addItemDecoration(new SpaceItemDecoration());
    }

    /**
     * 设置每个item之间的间距，让每个图片的width=height。
     */
    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int space = AppUtils.dp2px(12);
            int w = getResources().getDisplayMetrics().widthPixels;
            int image_w = (w - AppUtils.dp2px(12) * 4) / 3;
            if (parent.getChildLayoutPosition(view) % 3 == 0) {
                outRect.left = space;
                outRect.right = space / 2;
            } else if (parent.getChildLayoutPosition(view) % 3 == 1) {
                outRect.right = outRect.left = space / 2;
            } else {
                outRect.left = space / 2;
                outRect.right = space;
            }
            outRect.bottom = space;
            if (parent.getChildLayoutPosition(view) == 0) outRect.top = space;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            layoutParams.height = image_w;
            view.setLayoutParams(layoutParams);
        }
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
            ImageData imageData = list.get(position);
            View itemView = holder.itemView;

            Glide.with(AppUtils.getContext()).load(imageData.imagePath).placeholder(
                    R.mipmap.ic_image_black_48dp
            ).skipMemoryCache(true).into(holder.show);
//            Bitmap bitmap = mImageManager.getBitmap(imageData.imageId, imageData.imagePartentId);
//            holder.show.setImageBitmap(bitmap);
            if (imageData.isSelect) {
                holder.status.setImageResource(R.mipmap.ic_check_box_black_24dp);
            } else {
                holder.status.setImageResource(R.mipmap.ic_check_box_outline_blank_black_24dp);
            }

            itemView.setOnClickListener(v -> {
                if (imageData.isSelect) {
                    selectImages.remove(imageData);
                    imageData.isSelect = false;
                    holder.status.setImageResource(R.mipmap.ic_check_box_outline_blank_black_24dp);
                } else {
                    selectImages.add(imageData);
                    imageData.isSelect = true;
                    holder.status.setImageResource(R.mipmap.ic_check_box_black_24dp);
                }
                MenuItem menuItem = mToolbar.getMenu().getItem(0);
                menuItem.setTitle("已选择图片数量：" + selectImages.size() + "张");
            });
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_show)
        ImageView show;
        @Bind(R.id.iv_status)
        ImageView status;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    protected void initToolbar(Toolbar mToolbar, int resId) {
        mToolbar.setTitle(resId);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent();
            ImageFile imageFile = new ImageFile(selectImages);
            intent.putExtra("imageFile", imageFile);
            setResult(1, intent);
            finish();
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent = new Intent();
            ImageFile imageFile = new ImageFile(selectImages);
            intent.putExtra("imageFile", imageFile);
            setResult(1, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.iamge_select_menu, menu);
        return true;
    }
}
