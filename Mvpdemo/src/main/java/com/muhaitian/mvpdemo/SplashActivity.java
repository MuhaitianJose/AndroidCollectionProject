package com.muhaitian.mvpdemo;

import android.os.Bundle;

import com.muhaitian.mvpdemo.base.BaseActivity;
import com.muhaitian.mvpdemo.utils.RxHelper;
import com.muhaitian.mvpdemo.widget.SimpleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by muhaitian on 2017/9/7.
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.SB_skip)
    SimpleButton SBSkip;

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

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void doSkip() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.SB_skip)
    public void onViewClicked() {
    }
}
