package com.muhaitian.mvpdemo.injector.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by muhaitian on 2017/9/11.
 */
@Module
public class ApplicationModule {
    private final Context mContext;

    public ApplicationModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    @Singleton
    Context priderApplicationContext() {
        return mContext;
    }
}
