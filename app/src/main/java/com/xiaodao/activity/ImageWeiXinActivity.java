package com.xiaodao.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xiaodao.R;
import com.xiaodao.base.BaseActivity;
import com.xiaodao.base.DefaultAdapter;
import com.xiaodao.bean.ImageData;
import com.xiaodao.log.XLog;
import com.xiaodao.util.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by android on 2016/4/8.
 */
public class ImageWeiXinActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.root)
    LinearLayout mRoot;

    private Map<Integer, ImageData.ImageFileItem> names = new HashMap<>();
    private Map<Integer, List<ImageData>> imageDataList = new HashMap<>();

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_weixin);
        ButterKnife.bind(this);
        initToolbar(mToolbar, R.string.image_weixin);
    }

    @OnClick(R.id.image_weixin)
    public void imageWeixin() {
        Observable.create(subscriber -> {
            updateImages();
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor = MediaStore.Images.Media.query(getContentResolver(), uri,
                    new String[]{
                            MediaStore.Images.ImageColumns.DATA,
                            MediaStore.Images.ImageColumns._ID,
                            MediaStore.Images.ImageColumns.BUCKET_ID,
                            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                            MediaStore.Images.ImageColumns.MINI_THUMB_MAGIC,
                    }
            );
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String imagePath = cursor.getString(0);
                    int id = cursor.getInt(1);
                    int bucket_id = cursor.getInt(2);
                    String name = cursor.getString(3);
                    if (names.get(bucket_id) == null) {
                        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(getContentResolver(),
                                id, bucket_id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
                        names.put(bucket_id, new ImageData.ImageFileItem(name, bitmap));
                        imageDataList.put(bucket_id, new ArrayList<ImageData>());
                    }
                    ImageData imageData = new ImageData(imagePath, id, bucket_id);
                    imageDataList.get(bucket_id).add(imageData);
                    names.get(bucket_id).imageCount++;
                }
                cursor.close();
            }
            subscriber.onNext(names);
        }).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                XLog.i("onError");
            }

            @Override
            public void onNext(Object o) {
                XLog.i("onNext");
                showList();
            }
        });
    }


    private PopupWindow mPopupWindow;

    private void showList() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) return;
        View view = View.inflate(this, R.layout.popup_image, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        List<ImageData.ImageFileItem> list = new ArrayList<>();
        Set<Map.Entry<Integer, ImageData.ImageFileItem>> entries = names.entrySet();
        for (Map.Entry<Integer, ImageData.ImageFileItem> entry : entries) {
            list.add(entry.getValue());
        }
        mAdapter = new ImageAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                (int) (getResources().getDisplayMetrics().heightPixels * 0.6));
        mPopupWindow.setContentView(view);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setAnimationStyle(R.style.PopupWindow);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(mRoot, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 0.7f;
        getWindow().setAttributes(attributes);
        mPopupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 1.0f;
            getWindow().setAttributes(layoutParams);
        });
    }

    private void updateImages() {
        MediaScannerConnection.scanFile(this,
                new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()},
                null, null);
    }

    private class ImageAdapter extends DefaultAdapter<ImageData.ImageFileItem, ImageViewHolder> {

        public ImageAdapter(List<ImageData.ImageFileItem> list) {
            super(list);
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageViewHolder(View.inflate(AppUtils.getContext(),
                    R.layout.item_image, null));
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            ImageData.ImageFileItem item = list.get(position);
            holder.imageView.setImageBitmap(item.fileBitmap);
            holder.path.setText("文件夹：" + item.imagePath);
            holder.count.setText("数量：" + item.imageCount);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(AppUtils.getContext(),ImageFileActivity.class);
                startActivity(intent);
            });
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        ImageView imageView;
        @Bind(R.id.name)
        TextView path;
        @Bind(R.id.count)
        TextView count;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weixin_menu, menu);
        return true;
    }
}
