package com.muhaitian.mvpdemo.module.news.newslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.muhaitian.mvpdemo.R;
import com.muhaitian.mvpdemo.adapter.NewsMultiItem;
import com.muhaitian.mvpdemo.api.bean.NewsInfo;
import com.muhaitian.mvpdemo.base.BaseFragment;

import java.util.List;

import butterknife.BindView;


/**
 * Created by muhaitian on 2017/9/14.
 */

public class NewsListFragment extends BaseFragment implements INewsListView {

    public static final String NEWS_TYPE_KEY = "news_type_key";

    @BindView(R.id.rv_news_list)
    RecyclerView rvNewsList;


    private SliderLayout mAdSider;
    private String mNewsId;

    public static NewsListFragment newInstance(String newsId) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_TYPE_KEY, newsId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsId = getArguments().getString(NEWS_TYPE_KEY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdSider != null) {
            mAdSider.startAutoCycle();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdSider != null) {
            mAdSider.stopAutoCycle();
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }


    @Override
    public void loadData(List<NewsMultiItem> data) {

    }

    @Override
    public void loadMoreData(List<NewsMultiItem> data) {

    }

    @Override
    public void loadNodata() {

    }

    @Override
    public void loadAdData(NewsInfo newsBean) {

    }


}
