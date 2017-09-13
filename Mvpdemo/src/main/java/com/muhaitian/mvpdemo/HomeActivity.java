package com.muhaitian.mvpdemo;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.muhaitian.mvpdemo.base.BaseActivity;
import com.muhaitian.mvpdemo.module.image.main.PhotoMainFragment;
import com.muhaitian.mvpdemo.module.news.main.NewsMainFragment;

import com.muhaitian.mvpdemo.module.video.main.VideoMainFragment;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private SparseArray<String> mSparseTags = new SparseArray<>();

    private int mItemId = -1;
    private long mExitTime = -1;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case R.id.nav_news:
                    replaceFragment(R.id.fl_container, new NewsMainFragment(), mSparseTags.get(R.id.nav_news));
                    break;
                case R.id.nav_iamge:
                    replaceFragment(R.id.fl_container, new PhotoMainFragment(), mSparseTags.get(R.id.nav_iamge));
                    break;
                case R.id.nav_video:
                    replaceFragment(R.id.fl_container, new VideoMainFragment(), mSparseTags.get(R.id.nav_video));
                    break;
                case R.id.nav_setting:
                    break;
            }
            mItemId = -1;
            return true;
        }
    });

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initInjector() {
        Log.d(TAG, "initInjector: ");
    }

    @Override
    protected void initViews() {
        Log.d(TAG, "initViews: ");
        initDrawerLayout(drawerLayout, navView);
        mSparseTags.put(R.id.nav_news, "news");
        mSparseTags.put(R.id.nav_news, "photos");
        mSparseTags.put(R.id.nav_news, "video");
        getPermissions();
    }

    private void initDrawerLayout(DrawerLayout drawerLayout, NavigationView navView) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Log.d(TAG, "initDrawerLayout: ");
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | layoutParams.flags);
            drawerLayout.setFitsSystemWindows(true);
            drawerLayout.setClipToPadding(true);
        }
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d(TAG, "onDrawerClosed: ");
                mHandler.sendEmptyMessage(mItemId);
            }
        });
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        Log.d(TAG, "updateViews: ");
        navView.setCheckedItem(R.id.nav_news);
        addFragment(R.id.fl_container, new NewsMainFragment(), "news");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected: ");
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.isChecked()) {
            return true;
        }
        mItemId = item.getItemId();
        Log.d(TAG, "onNavigationItemSelected: mItemId==" + mItemId);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getPermissions() {

    }

    @Override
    public void onBackPressed() {
        final int stackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (stackEntryCount == 1) {
            exit();
        } else {
            final String name = getSupportFragmentManager().getBackStackEntryAt(stackEntryCount - 2).getName();
            int index = mSparseTags.indexOfValue(name);
            navView.setCheckedItem(mSparseTags.keyAt(index));
            super.onBackPressed();
        }
    }

    private void exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
