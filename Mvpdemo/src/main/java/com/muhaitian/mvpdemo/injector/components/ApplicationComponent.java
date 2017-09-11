package com.muhaitian.mvpdemo.injector.components;

import android.content.Context;

import com.muhaitian.mvpdemo.injector.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

/**
 * Created by muhaitian on 2017/9/8.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context providerContext();
}
