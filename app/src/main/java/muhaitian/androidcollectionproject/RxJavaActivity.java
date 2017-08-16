package muhaitian.androidcollectionproject;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;
import muhaitian.androidcollectionproject.model.Student;
import muhaitian.androidcollectionproject.rxjava.RxJavaTestDemo;
import muhaitian.androidcollectionproject.rxjava.RxJavaUsageScenarios;


/**
 * Created by muhaitian on 2017/8/7.
 */

public class RxJavaActivity extends AppCompatActivity {

    private final String TAG = "RxJavaActivity";
    private RxJavaUsageScenarios rxJavaUsageScenarios;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rajava_main_view);
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll().penaltyLog().build());
        }
        PublishSubject<Integer> mDownloadProgress = PublishSubject.create();


        rxJava_01();
        rxJava_02();
        rxJava_03();
        rxJavaMap();
        rxJavaFlatmap();
        rxJavaUsageScenarios = new RxJavaUsageScenarios();
        rxJavaUsageScenarios.useFilter();

//        RxJavaTestDemo.Samplingc();
//        RxJavaTestDemo.Timeout();
//        RxJavaTestDemo.flatMap();
//        RxJavaTestDemo.mapDemo();
//        RxJavaTestDemo.ConcatMap();
//        RxJavaTestDemo.Scan();
//        RxJavaTestDemo.GroupBy();
//        RxJavaTestDemo.Buffer();
//        RxJavaTestDemo.Window();
//        RxJavaTestDemo.cast();
//        RxJavaTestDemo.Merge();
//        RxJavaTestDemo.useZip();
//        RxJavaTestDemo.useJoin();
//        RxJavaTestDemo.StartWith();
//        RxJavaTestDemo.allObserver();
//        RxJavaTestDemo.ambObserver();
//        RxJavaTestDemo.ContainsAndIsEmpty();
//        RxJavaTestDemo.DefaultIfEmpty();
//        RxJavaTestDemo.SequenceEqual();
//        RxJavaTestDemo.SkipUntilAndSkipWhile();
//        RxJavaTestDemo.TakeUntilAndTakeWhile();
        RxJavaTestDemo.ReduceAndCollect();
    }


    private void rxJava_01() {

        Observer<String> mySubscriber = new Observer<String>() {


            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "rxjava_01 onNext: " + s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "rxjava_01 onComplete: ");
            }
        };
        Observable<String> myObservable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull ObservableEmitter e) throws Exception {
                e.onNext("demo first call");
                e.onComplete();
            }
        });

        myObservable.subscribe(mySubscriber);

    }

    private void rxJava_02() {
        Observer<String> stringObserver = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "rxJava_02 onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "rxJava_02 onComplete: ");
            }
        };
        Observable<String> observable = Observable.fromArray("I use Observable.fromArray");
        observable.subscribe(stringObserver);

    }

    private void rxJava_03() {
        Observable.just("rajava from").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "onNext: rxJava_03 " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxJavaMap() {
        Observable.just("hello rxJava map").map(new Function<String, String>() {
            @Override
            public String apply(@NonNull String s) throws Exception {
                return s + "muhaitian";
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(@NonNull String s) throws Exception {
                return s.hashCode();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer s) {
                Log.d(TAG, "onNext: rxJavaMap " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    //调用中使用对象
    private void rxJavaFlatmap() {
        Log.d(TAG, "rxJavaFlatmap: ");
        Student user1 = new Student();
        user1.setName("Jose");
        Student user2 = new Student();
        user2.setName("muhaitian");
        List<Student> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);


        qury(list).flatMap(new Function<List<Student>, ObservableSource<Student>>() {
            @Override
            public ObservableSource<Student> apply(@NonNull final List<Student> strings) throws Exception {
                return Observable.create(new ObservableOnSubscribe<Student>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Student> e) throws Exception {
                        for (int i = 0; i < strings.size(); i++)
                            e.onNext(strings.get(i));
                        e.onComplete();
                    }
                });
            }
        }).subscribe(new Observer<Student>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "rxJavaFlatmap onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Student o) {
                Log.d(TAG, "onNext: " + o);
                Log.d(TAG, "rxJavaFlatmap onNext: " + o.getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "rxJavaFlatmap onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "rxJavaFlatmap onComplete: ");

            }
        });
        Log.d(TAG, "rxJavaFlatmap: over");
    }

    private Observable<List<Student>> qury(final List<Student> list) {
        return Observable.create(new ObservableOnSubscribe<List<Student>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Student>> e) throws Exception {
                e.onNext(list);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
