package com.muhaitian.mvpdemo.module.video.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.muhaitian.mvpdemo.R;
import com.muhaitian.mvpdemo.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by muhaitian on 2017/9/12.
 */

public class VideoMainFragment extends BaseFragment implements IVideoMainView {
    @BindView(R.id.iv_love_count)
    TextView ivLoveCount;
    @BindView(R.id.tv_download_count)
    TextView tvDownloadCount;


    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
//    Unbinder unbinder;

    @BindView(R.id.fl_love_layout)
    FrameLayout flLoveLayout;
    @BindView(R.id.fl_download_layout)
    FrameLayout flDownloadLayout;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_video_main;
    }

    @Override
    protected void initViews() {
        initToolBar(toolBar, true, "视频");
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public void updateLovedCount(int lovedcount) {

    }

    @Override
    public void updateDownloadCount(int downloadcount) {

    }


}
