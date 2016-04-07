package com.xiaodao.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaodao.R;
import com.xiaodao.base.BaseActivity;
import com.xiaodao.fragment.Tab_1_Fragment;
import com.xiaodao.fragment.Tab_2_Fragment;
import com.xiaodao.fragment.Tab_3_Fragment;
import com.xiaodao.log.XLog;
import com.xiaodao.util.AppUtils;
import com.xiaodao.util.Constants;
import com.xiaodao.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tab_main)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.root)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;

    private TextView mUserName;
    private ImageView mUserAvatar;

    private HomeAdapter mAdapter;

    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        initDrawer();

        if (savedInstanceState == null){
            initFragment();
        }
    }

    private void initDrawer() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.open,R.string.close);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        mUserAvatar = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.iv_head);
        mUserAvatar.setOnClickListener(v -> startActivity(new Intent(AppUtils.getContext(),ImageActivity.class)));
        mUserName = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.tv_head);
        mUserName.setText(SPUtils.getString(Constants.USERNAME, null));
        mNavigationView.setCheckedItem(R.id.tab_1);
        mNavigationView.setNavigationItemSelectedListener(item -> {
            mNavigationView.setCheckedItem(item.getItemId());
            Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
            mDrawerLayout.closeDrawer(mNavigationView);
            switch (item.getItemId()) {
                case R.id.tab_1:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.tab_2:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.tab_3:
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.exit:
                    exit();
                    break;
            }
            return true;
        });
    }

    private void exit() {
        SPUtils.remove(Constants.USERNAME);
        startActivity(new Intent(this,LoginActivity.class));
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void initFragment() {
        mTitles.add("首页");
        mTitles.add("学习");
        mTitles.add("个人");
        mFragments.add(new Tab_1_Fragment());
        mFragments.add(new Tab_2_Fragment());
        mFragments.add(new Tab_3_Fragment());

        mAdapter = new HomeAdapter(getSupportFragmentManager(),mTitles,mFragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mNavigationView.setCheckedItem(R.id.tab_1);
                        break;
                    case 1:
                        mNavigationView.setCheckedItem(R.id.tab_2);
                        break;
                    case 2:
                        mNavigationView.setCheckedItem(R.id.tab_3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class HomeAdapter extends FragmentPagerAdapter{

        private List<String> titles;
        private List<Fragment> fragments;

        public HomeAdapter(FragmentManager fm,List<String> titles,List<Fragment> fragments) {
            super(fm);
            this.titles = titles;
            this.fragments = fragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
