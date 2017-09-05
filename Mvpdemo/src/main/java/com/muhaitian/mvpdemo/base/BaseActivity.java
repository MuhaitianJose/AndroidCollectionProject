package com.muhaitian.mvpdemo.base;


import android.support.annotation.LayoutRes;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by Muhaitian on 03/09/2017.
 */

public abstract class BaseActivity extends RxAppCompatActivity implements IBaseView {

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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNetError() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return null;
    }

    @Override
    public void finishRefresh() {

    }
}
