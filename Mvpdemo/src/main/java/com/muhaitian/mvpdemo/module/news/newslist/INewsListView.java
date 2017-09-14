package com.muhaitian.mvpdemo.module.news.newslist;

import com.muhaitian.mvpdemo.adapter.NewsMultiItem;
import com.muhaitian.mvpdemo.api.bean.NewsInfo;
import com.muhaitian.mvpdemo.base.ILoadDataView;

import java.util.List;

/**
 * Created by muhaitian on 2017/9/14.
 */

public interface INewsListView extends ILoadDataView<List<NewsMultiItem>> {
    void loadAdData(NewsInfo newsBean);
}
