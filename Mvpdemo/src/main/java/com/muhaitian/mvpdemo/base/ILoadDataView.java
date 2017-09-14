package com.muhaitian.mvpdemo.base;

/**
 * Created by muhaitian on 2017/9/14.
 */

public interface ILoadDataView<T> extends IBaseView {
    /**
     * 加载数据
     *
     * @param data
     */
    void loadData(T data);

    /**
     * 加载更多
     *
     * @param data
     */
    void loadMoreData(T data);

    /**
     * 没有数据
     */
    void loadNodata();
}
