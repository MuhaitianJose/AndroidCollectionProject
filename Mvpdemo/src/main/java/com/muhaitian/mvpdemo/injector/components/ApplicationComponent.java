package com.muhaitian.mvpdemo.injector.components;

import android.content.Context;

import dagger.Provides;

/**
 * Created by muhaitian on 2017/9/8.
 */

public interface ApplicationComponent {

    @Provides
    Context providerContext();
}
