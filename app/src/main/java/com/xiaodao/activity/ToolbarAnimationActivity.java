package com.xiaodao.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.xiaodao.R;
import com.xiaodao.base.BaseActivity;
import com.xiaodao.util.AppUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 2016/4/9.
 */
public class ToolbarAnimationActivity extends BaseActivity {

    // TODO: 2016/4/10 没有解决状态栏的透明化
    
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_animation);
        ButterKnife.bind(this);
        initToolbar(mToolbar, R.string.animation_toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        TestAdapter testAdapter = new TestAdapter();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(testAdapter);
    }

    public class TestAdapter extends RecyclerView.Adapter<TestViewHolder> {

        @Override
        public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TestViewHolder(View.inflate(
                    AppUtils.getContext(),
                    R.layout.item,
                    null
            ));
        }

        @Override
        public void onBindViewHolder(TestViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {

        public TestViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_animation,menu);
        return true;
    }
}
