package com.xiaodao.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaodao.R;
import com.xiaodao.base.BaseActivity;
import com.xiaodao.log.XLog;
import com.xiaodao.util.AppUtils;
import com.xiaodao.util.Constants;
import com.xiaodao.widget.CircleImageView;

import java.io.File;

import javax.xml.xpath.XPath;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by android on 2016/4/6.
 */
public class ImageActivity extends BaseActivity {

    private static final int IMAGE_LOCAL = 1;

    private String mTempPath;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv_show)
    CircleImageView mShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        initToolbar(mToolbar, R.string.image_title);
    }

    @OnClick(R.id.image_local)
    /**
     * 调用系统图库查看图片
     */
    public void imageLocal() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_LOCAL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_LOCAL:
                    getImage_Local(data);
                    break;
            }
        }
    }

    private void getImage_Local(Intent data) {

        Observable<String> observable = Observable.create(subscriber -> {
            Uri uri = data.getData();
            CursorLoader cursorLoader = new CursorLoader(AppUtils.getContext()
                    , uri, new String[]{
                    MediaStore.Images.ImageColumns.DATA,
            }, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
                mTempPath = cursor.getString(columnIndex);
                cursor.close();
            }
            XLog.i("image path :" + mTempPath);
            subscriber.onNext(mTempPath);
            subscriber.onCompleted();
        });

        observable.subscribe(s -> {
            Glide.with(AppUtils.getContext()).load(s).into(mShow);
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.image_local:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(Constants.TITLE, R.string.image_local);
                break;
            case R.id.image_camera:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(Constants.TITLE, R.string.image_camera);
                break;
            case R.id.image_cut:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(Constants.TITLE, R.string.image_cut);
                break;
        }
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_menu, menu);
        return true;
    }
}
