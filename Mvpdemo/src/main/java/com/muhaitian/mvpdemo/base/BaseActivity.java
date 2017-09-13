package com.muhaitian.mvpdemo.base;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.muhaitian.mvpdemo.R;
import com.muhaitian.mvpdemo.utils.SwipeRefreshHelper;
import com.muhaitian.mvpdemo.widget.EmptyLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Muhaitian on 03/09/2017.
 */

public abstract class BaseActivity extends RxAppCompatActivity implements IBaseView, EmptyLayout.OnRetryListener {
    @Nullable
    @BindView(R.id.empty_layout)
    protected EmptyLayout emptyLayout;

    @Nullable
    @BindView(R.id.swipe_refresh)
    protected SwipeRefreshLayout swipeRefreshLayout;

    /**
     * 绑定布局
     *
     * @return
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * 依赖注入
     */
    protected abstract void initInjector();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 更新视图空间
     *
     * @param isRefresh
     */
    protected abstract void updateViews(boolean isRefresh);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());
        ButterKnife.bind(this);
        initInjector();
        initViews();
        initSwipeRefresh();
        updateViews(false);
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (swipeRefreshLayout != null) {
            SwipeRefreshHelper.init(swipeRefreshLayout, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateViews(true);
                }
            });
        }
    }

    @Override
    public void showLoading() {
        if (emptyLayout != null) {
            emptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    @Override
    public void hideLoading() {
        if (emptyLayout != null) {
            emptyLayout.hide();
        }
    }

    @Override
    public void showNetError() {
        if (emptyLayout != null) {
            emptyLayout.setEmptyStatus(EmptyLayout.STATUS_NET_ERROR);
            emptyLayout.setRetryListener(this);
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    public void finishRefresh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRetry() {
        updateViews(false);
    }

    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnable, String Title) {
        toolbar.setTitle(Title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnable);
    }

    protected void initToolbar(Toolbar toolbar, boolean homeAsUpEnable, int resTitle) {
        initToolBar(toolbar, homeAsUpEnable, getString(resTitle));
    }

    /**
     * 添加Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(containerViewId, fragment);
        transaction.commit();
    }

    /**
     * @param containerViewId
     * @param fragment
     * @param tag
     */
    protected void addFragment(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //设置tag,不然下面findFragmentByTag()会找不到
        transaction.add(containerViewId, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    /**
     * @param containerViewId
     * @param fragment
     */
    protected void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerViewId, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * @param containerViewId
     * @param fragment
     * @param tag
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(containerViewId, fragment, tag);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(tag);
            transaction.commit();
        } else {
            getSupportFragmentManager().popBackStack(tag, 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            Log.d("onOptionsItemSelected", "onOptionsItemSelected: ");
//            finish();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
