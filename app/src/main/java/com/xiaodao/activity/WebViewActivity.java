package com.xiaodao.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.xiaodao.R;
import com.xiaodao.base.BaseActivity;
import com.xiaodao.util.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 2016/4/6.
 */
public class WebViewActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private int mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        mTitle = getIntent().getIntExtra(Constants.TITLE,0);
        initToolbar(mToolbar,mTitle);
    }
}
