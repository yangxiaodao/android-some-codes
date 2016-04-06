package com.xiaodao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaodao.R;
import com.xiaodao.base.BaseActivity;
import com.xiaodao.util.Constants;
import com.xiaodao.util.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by android on 2016/4/6.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.text_ly)
    TextInputLayout mTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mToolbar.setTitle(R.string.login_title);
        setSupportActionBar(mToolbar);
        SPUtils.logSP();
        String name = SPUtils.getString(Constants.USERNAME,null);
        if (!TextUtils.isEmpty(name)){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    @OnClick(R.id.login)
    public void login(){
        EditText editText = mTextInputLayout.getEditText();
        String name = editText.getText().toString().trim();
        if (TextUtils.isEmpty(name) || name.length()>8){
            Toast.makeText(this,"请输入正确的用户名",Toast.LENGTH_LONG).show();
            return;
        }
        SPUtils.saveString(Constants.USERNAME,name);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
