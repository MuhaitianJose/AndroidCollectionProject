package com.muhaitian.mvpdemo.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Muhaitian on 03/09/2017.
 */

public class EmptyLayout extends FrameLayout {

    public static final int STATUS_HIDE = 1024;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NET_ERROR = 2;
    public static final int STATUS_NO_DATA = 2;

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
    }

    public EmptyLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EmptyLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public interface OnRetryListener {
        void onRetry();
    }
}
