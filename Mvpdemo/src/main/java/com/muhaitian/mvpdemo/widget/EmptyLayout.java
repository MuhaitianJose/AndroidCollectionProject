package com.muhaitian.mvpdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.muhaitian.mvpdemo.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Muhaitian on 03/09/2017.
 */

public class EmptyLayout extends FrameLayout {

    public static final int STATUS_HIDE = 1024;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NET_ERROR = 2;
    public static final int STATUS_NO_DATA = 3;

    @BindView(R.id.tv_net_error)
    TextView mTvNetError;

    @BindView(R.id.rl_empty_container)
    FrameLayout mRlEmptyContainer;
    @BindView(R.id.empty_loading)
    SpinKitView mEmptyLoading;
    @BindView(R.id.empty_layout)
    FrameLayout mEmptyLayout;

    private Context mContext;
    private OnRetryListener retryListener;
    private int mEmptyStatus = STATUS_LOADING;
    private int bgColor;

    public EmptyLayout(@NonNull Context context) {
        super(context);
    }

    public EmptyLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        try {
            bgColor = a.getColor(R.styleable.EmptyLayout_background_color, Color.WHITE);
        } finally {
            a.recycle();
        }
        View.inflate(mContext, R.layout.layout_empty_loading, this);
        ButterKnife.bind(this);
        mEmptyLayout.setBackgroundColor(bgColor);
        switchEmptyView();
    }

    private void switchEmptyView() {
        switch (mEmptyStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mRlEmptyContainer.setVisibility(GONE);
                mEmptyLoading.setVisibility(VISIBLE);
                break;
            case STATUS_NET_ERROR:
            case STATUS_NO_DATA:
                setVisibility(VISIBLE);
                mEmptyLoading.setVisibility(GONE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
        }
    }

    /**
     * 隐藏视图
     */
    public void hide() {
        mEmptyStatus = STATUS_HIDE;
        switchEmptyView();
    }

    /**
     * 设置视图状态
     */
    public void setEmptyStatus(int emptystatus) {
        mEmptyStatus = emptystatus;
        switchEmptyView();
    }

    /**
     * 返回视图状态
     *
     * @return
     */
    public int getEmptyStatus() {
        return mEmptyStatus;
    }

    /**
     * 设置监听器
     *
     * @param retryListener
     */
    public void setRetryListener(OnRetryListener retryListener) {
        this.retryListener = retryListener;
    }

    @OnClick(R.id.tv_net_error)
    public void onClick() {
        if (retryListener != null) {
            retryListener.onRetry();
        }
    }

    public interface OnRetryListener {
        void onRetry();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_NET_ERROR, STATUS_NO_DATA})
    public @interface EmptyStatus {
    }
}
