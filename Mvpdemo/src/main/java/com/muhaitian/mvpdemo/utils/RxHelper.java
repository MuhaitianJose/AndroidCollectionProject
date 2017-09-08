package com.muhaitian.mvpdemo.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by muhaitian on 2017/9/8.
 */

public class RxHelper {
    public RxHelper() {
        throw new AssertionError();
    }

    public static Observable<Integer> countDown(int time) {
        if (time < 0) {
            time = 0;
        }
        final int countTime = time;
        return Observable
                .interval(0, 1, TimeUnit.SECONDS).map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(@NonNull Long aLong) throws Exception {
                        return countTime - aLong.intValue();
                    }
                })
                .take(countTime + 1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
