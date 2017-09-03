package com.muhaitian.mvpdemo.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by Muhaitian on 03/09/2017.
 */

public interface IBaseView {
    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示网络错误
     */
    void showNetError();

    /**
     * 绑定生命周期
     *
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();

    /**
     * 完成刷新，新增控制刷新
     */
    void finishRefresh();
}
