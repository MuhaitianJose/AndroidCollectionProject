package com.muhaitian.mvpdemo.module.video.main;

/**
 * Created by muhaitian on 2017/9/12.
 */

public interface IVideoMainView {
    /**
     * 更新数据
     *
     * @param lovedcount 收藏数目
     */
    void updateLovedCount(int lovedcount);

    /**
     * 更新数据
     *
     * @param downloadcount 下载中的个数
     */
    void updateDownloadCount(int downloadcount);
}
