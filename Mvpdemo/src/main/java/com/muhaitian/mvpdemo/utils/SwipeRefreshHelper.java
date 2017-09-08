package com.muhaitian.mvpdemo.utils;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by muhaitian on 2017/9/8.
 */

public class SwipeRefreshHelper {

    public SwipeRefreshHelper() {
        throw new AssertionError();
    }

    /**
     * 初始化，关联AppBarLayout，处理滑动冲突
     *
     * @param refreshLayout
     * @param barLayout
     * @param refreshListener
     */
    public static void init(final SwipeRefreshLayout refreshLayout, AppBarLayout barLayout, SwipeRefreshLayout.OnRefreshListener refreshListener) {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright
                , android.R.color.holo_green_light
                , android.R.color.holo_orange_light
                , android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(refreshListener);
        barLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    refreshLayout.setEnabled(true);
                } else {
                    refreshLayout.setEnabled(false);
                }
            }
        });
    }

    /**
     * 初始化
     *
     * @param refreshLayout
     * @param refreshListener
     */
    public static void init(final SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener refreshListener) {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright
                , android.R.color.holo_green_light
                , android.R.color.holo_orange_light
                , android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(refreshListener);
    }

    /**
     * 设置刷新开关
     *
     * @param refreshLayout
     * @param isEnble
     */
    public static void enableRefresh(SwipeRefreshLayout refreshLayout, boolean isEnble) {
        if (refreshLayout != null) {
            refreshLayout.setEnabled(isEnble);
        }
    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     * @param isRefresh
     */
    public static void controlRefresh(SwipeRefreshLayout refreshLayout, boolean isRefresh) {
        if (refreshLayout != null) {
            if (isRefresh != refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(isRefresh);
            }
        }
    }
}
