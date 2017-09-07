package com.muhaitian.mvpdemo.base.injector;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by muhaitian on 2017/9/7.
 *
 * 单例标签
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
