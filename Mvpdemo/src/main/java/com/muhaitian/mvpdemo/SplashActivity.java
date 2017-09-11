package com.muhaitian.mvpdemo;

import android.content.Intent;
import android.util.Log;

import com.muhaitian.mvpdemo.base.BaseActivity;
import com.muhaitian.mvpdemo.utils.RxHelper;
import com.muhaitian.mvpdemo.widget.SimpleButton;

import butterknife.BindView;

import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by muhaitian on 2017/9/7.
 */

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @BindView(R.id.SB_skip)
    SimpleButton SBSkip;

    private boolean mIsSkip = false;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        RxHelper.countDown(5).compose(this.<Integer>bindToLife()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: " + integer);
                SBSkip.setText("跳过" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                doSkip();
            }

            @Override
            public void onComplete() {
                doSkip();
            }
        });
    }

    private void doSkip() {
        Log.d(TAG, "doSkip: ");
        if (!mIsSkip) {
            mIsSkip = true;
            finish();
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
        }
    }


    @OnClick(R.id.SB_skip)
    public void onViewClicked() {
        Log.d(TAG, "onViewClicked: ");
        doSkip();
    }
}
