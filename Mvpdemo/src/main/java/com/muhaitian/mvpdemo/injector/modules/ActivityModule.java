package com.muhaitian.mvpdemo.injector.modules;

import android.app.Activity;

import com.muhaitian.mvpdemo.base.injector.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by muhaitian on 2017/9/7.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    Activity getActivity() {
        return activity;
    }

}
