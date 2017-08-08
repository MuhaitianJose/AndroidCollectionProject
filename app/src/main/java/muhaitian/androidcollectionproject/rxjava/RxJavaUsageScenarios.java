package muhaitian.androidcollectionproject.rxjava;


import java.util.concurrent.TimeUnit;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import muhaitian.androidcollectionproject.R;
import rx.functions.Action0;

/**
 * Created by muhaitian on 2017/8/8.
 */

public class RxJavaUsageScenarios {

    private static final String TAG = "RxJavaUsageScenarios";

    /**
     * 第一种场景
     * <p>
     * 这种场景适用于后台获取数据，在主线程显示数据
     */
    public void useageScheduler() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                //这里可以进行一些耗时操作，这里的操作是在Schedulers.io()中进行，不是
                //在UI线程，不会卡死或者卡顿
                String[] data = httpCall().split(",");
                for (int i = 0; i < data.length; i++)
                    e.onNext(data[i]);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "useageScheduler onSubscribe: " + d);
                    }

                    @Override
                    public void onNext(@NonNull String str) {
                        Log.d(TAG, "useageScheduler onNext: " + str);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "useageScheduler onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "useageScheduler onComplete: ");
                    }
                });
    }

    public String httpCall() {
        return "Lucy,Lily,Jose,Tom,Jimmy,Jack,";
    }

    /**
     * 第二种场景
     * 使用debounce做textSearch,简单来讲就是当N个节点发生的时间靠的太近（就是小于设定的时间T）
     * debounce会过滤掉前N-1个节点，比如百度做地址联想是，减少频繁的网络请求
     */

    public void useDebounce(EditText editText) {
        RxTextView.
                textChangeEvents(editText).
                debounce(400, TimeUnit.MILLISECONDS).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TextViewTextChangeEvent>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "useDebounce onSubscribe: " + d);
            }

            @Override
            public void onNext(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) {
                Log.d(TAG, "useDebounce onNext: " + textViewTextChangeEvent.text().toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "useDebounce onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "useDebounce onComplete: ");
            }
        });
    }

    /**
     * 第三种场景
     * 使用combineLatest合并最近的N个节点。
     */
    public void useCombineLatest(final EditText email, final EditText passwd, final EditText number, final Button btn) {
        Observable<CharSequence> emailObservable = RxTextView.textChanges(email).skip(1);
        Observable<CharSequence> passWdObservable = RxTextView.textChanges(passwd).skip(1);
        Observable<CharSequence> numberObservable = RxTextView.textChanges(number).skip(1);

        Observable.combineLatest(emailObservable, passWdObservable, numberObservable,
                new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull CharSequence charSequence, @NonNull CharSequence charSequence2,
                                         @NonNull CharSequence charSequence3) throws Exception {
                        boolean emailValid = !TextUtils.isEmpty(charSequence);
                        if (!emailValid) {
                            email.setError("Invalid Email");
                        }

                        boolean passwdValid = !TextUtils.isEmpty(charSequence2);
                        if (!passwdValid) {
                            passwd.setError("Invalid Password");
                        }

                        boolean numberValid = !TextUtils.isEmpty("charSequence3");

                        if (!numberValid) {
                            number.setError("Invalid Number");
                        }

                        return emailValid && passwdValid && numberValid;
                    }
                }).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Boolean aBoolean) {
                btn.setEnabled(aBoolean);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    /**
     * 第四种场景
     * <p>
     * 使用merge 合并数据源
     */
    public void useMerge() {

        Observable.
                merge(Observable.just("jose", "lucy"), Observable.just("lily", "tommy")).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "useMerge onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d(TAG, "useMerge onNext: " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "useMerge onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "useMerge onComplete: ");
                    }
                });

    }

    /**
     * 第五种场景
     * <p>
     * 使用concat和first做缓存，一次检查memory,disk,network是否存在数据，任何一步发现数据后面的操作就不执行了
     */
    public void useConcatAndFirst() {

        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                String memory = null;
                if (!TextUtils.isEmpty(memory)) {
                    e.onNext(memory);
                } else {
                    e.onComplete();
                }
            }
        });

        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                String disk = "lucy jose";
                if (!TextUtils.isEmpty(disk)) {
                    e.onNext(disk);
                } else {
                    e.onComplete();
                }
            }
        });

        Observable<String> network = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                String network = "network";
                if (!TextUtils.isEmpty(network)) {
                    e.onNext(network);
                } else {
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io());

        Observable.concat(memory, disk, network).first("muhaitian").subscribeOn(Schedulers.newThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "useConcatAndFirst accept: " + s);
            }
        });

    }

    /**
     * 第六种场景
     * <p>
     * 使用Timer做定时操作，比如x秒后执行y
     */
    public void useTimer() {
        Observable.timer(3, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "useTimer onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d(TAG, "useTimer onNext: " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "useTimer onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    /**
     * 第七种场景
     * <p>
     * 使用interval做周期性操作。当有“每隔xx秒后执行yy操作”类似的需求的时候，想到使用interval
     */

    public void useInterval() {

        Observable.interval(3, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "useInterval onSubscribe: " + d);
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d(TAG, "useInterval onNext: " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "useInterval onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "useInterval onComplete: ");
            }
        });

    }

    /**
     * 第八种场景
     * <p>
     * 使用throttleFirst防止重复点击
     */
    public void useThrottleFirst(Button clickButton) {
        RxView.clicks(clickButton).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "useThrottleFirst nSubscribe: " + d);
            }

            @Override
            public void onNext(@NonNull Object o) {
                Log.d(TAG, "useThrottleFirst onNext: " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "useThrottleFirst onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    /**
     * 第九种场景
     */
    public void useSchedulePeriodically() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Object> e) throws Exception {
                Schedulers.newThread().createWorker().schedulePeriodically(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 500, 400, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     *
     * 第十种场景
     *
     * 进行数组、list遍历
     *
     */
    public void ThroughArray(){
        String[] arrary = {"lucy","Lily","Tom","jose","muhaitian"};
        Observable.fromArray(arrary).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "ThroughArray onSubscribe: "+d);
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "ThroughArray onNext: "+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "ThroughArray onError: "+e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    /**
     *
     * 第十一种场景
     *
     * 解决嵌套回调,目前没有找到合适的例子
     *
     */
    public void NestedCallbacks(){

    }

    /**
     *
     * 第十二种场景
     *
     * 响应式界面
     *
     */
    public void correspondingUI(){
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);
//
//        Preference<Boolean> checked = rxPreferences.getBoolean("checked", true);
//
//        CheckBox checkBox = (CheckBox) findViewById(R.id.cb_test);
//        RxCompoundButton.checkedChanges(checkBox)
//                .subscribe(checked.asAction());
    }

}
