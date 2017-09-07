package com.muhaitian.mvpdemo.injector.components;

import android.app.Activity;

import com.muhaitian.mvpdemo.base.injector.PerActivity;
import com.muhaitian.mvpdemo.injector.modules.ActivityModule;

import dagger.Component;

/**
 * Created by muhaitian on 2017/9/7.
 */
@PerActivity
@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    Activity geActivity();
}
