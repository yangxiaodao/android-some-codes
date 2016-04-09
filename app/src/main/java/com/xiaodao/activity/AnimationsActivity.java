package com.xiaodao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.xiaodao.R;
import com.xiaodao.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by android on 2016/4/9.
 */
public class AnimationsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animations);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar,R.string.animations);
    }

    @OnClick(R.id.material_design)
    public void materialDesignToolbar(){
        startActivity(new Intent(this,ToolbarAnimationActivity.class));
    }

}
