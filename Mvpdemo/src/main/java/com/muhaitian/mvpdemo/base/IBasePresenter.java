package com.muhaitian.mvpdemo.base;

/**
 * Created by muhaitian on 2017/9/11.
 */

public interface IBasePresenter {
    /**
     *
     * 获取网络数据，更新界面
     *
     * @param isRefresh
     */
    void getData(boolean isRefresh);

    /**
     * 加载更多数据
     */
    void getMoreData();
}
