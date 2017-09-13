package com.muhaitian.mvpdemo.module.image.main;


import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.muhaitian.mvpdemo.R;
import com.muhaitian.mvpdemo.base.BaseFragment;

import butterknife.BindView;


/**
 * Created by muhaitian on 2017/9/12.
 */

public class PhotoMainFragment extends BaseFragment implements IPhotoMainView {
    @BindView(R.id.iv_count)
    TextView ivCount;

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    //    Unbinder unbinder;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;

    @Override
    public void updateCount(int lovedcount) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_photo_main;
    }

    @Override
    protected void initViews() {
        initToolBar(toolBar, true, "图片");
        setHasOptionsMenu(true);
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }


}
