package muhaitian.androidcollectionproject.rxjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import muhaitian.androidcollectionproject.mvc.Student;

/**
 * Created by muhaitian on 2017/8/9.
 */

public class RxJavaTestDemo {
    private static final String TAG = "RxJavaTestDemo";

    /**
     * 间隔时间发射信息,不知道为啥没有间隔打印信息。
     */
    public static void Samplingc() {
//        List<Integer> mkm = new ArrayList<>();
//        mkm.add(1);
//        mkm.add(2);
//        mkm.add(3);
//        mkm.add(4);
//        mkm.add(5);
//        mkm.add(6);
//        mkm.add(7);

        Observable integerObservable = Observable.just(12);
        integerObservable.sample(10, TimeUnit.MILLISECONDS).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "Samplingc onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "Samplingc onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Samplingc onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Samplingc onComplete: ");
            }
        });
    }

    /**
     * 测试在规定时间内没有得到数据，发射一个错误。
     * <p>
     * timeout(long,TimeUnit)： 第一个变体接受一个时长参数，每当原始Observable发射了一项数据，timeout就启动一个计时器，如果计时器超过了指定指定的时长而原始Observable没有发射另一项数据，timeout就抛出TimeoutException，以一个错误通知终止Observable。 这个timeout默认在computation调度器上执行，你可以通过参数指定其它的调度器。
     * timeout(long,TimeUnit,Observable)： 这个版本的timeout在超时时会切换到使用一个你指定的备用的Observable，而不是发错误通知。它也默认在computation调度器上执行。
     * timeout(Func1)：这个版本的timeout使用一个函数针对原始Observable的每一项返回一个Observable，如果当这个Observable终止时原始Observable还没有发射另一项数据，就会认为是超时了，timeout就抛出TimeoutException，以一个错误通知终止Observable。
     * timeout(Func1,Observable)： 这个版本的timeout同时指定超时时长和备用的Observable。它默认在immediate调度器上执行
     * timeout(Func0,Func1)：这个版本的time除了给每一项设置超时，还可以单独给第一项设置一个超时。它默认在immediate调度器上执行。
     * timeout(Func0,Func1,Observable)： 同上，但是同时可以指定一个备用的Observable。它默认在immediate调度器上执行。
     */

    public static void Timeout() {

        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull ObservableEmitter e) throws Exception {
                e.onNext(12);

            }
        });
        observable.timeout(2000, TimeUnit.MILLISECONDS).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "Timeout onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "Timeout onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Timeout onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Timeout onComplete: ");
            }
        });
    }

    /**
     * map使用方法
     * <p>
     * RxJava的 map 函数接收一个指定的 Func 对象然后将它应用到每一个由
     * Observable发射的值上。下图展示了如何将一个乘法函数应用到每个发出的值上以
     * 此创建一个新的Observable来发射转换的数据。
     */

    public static void mapDemo() {
        Observable.just("lucy", "jose", "muhaitian").map(new Function<String, Student>() {
            @Override
            public Student apply(@NonNull String s) throws Exception {
                Student student = new Student();
                student.setName(s);
                return student;
            }
        }).subscribe(new Observer<Student>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "mapDemo onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Student student) {
                Log.d(TAG, "mapDemo onNext: " + student.getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "mapDemo onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "mapDemo onComplete: ");
            }
        });
    }

    /**
     * flatMap
     */
    public static void flatMap() {
        Observable.just("12", "23", "34", "45", "56").flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull String s) throws Exception {
                return Observable.just(s + "flatMap");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "flatMap onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull String o) {
                Log.d(TAG, "flatMap onNext: " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "flatMap onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "flatMap onComplete: ");
            }
        });
    }

    /**
     * concatMap test
     * shuo
     */
    public static void ConcatMap() {
        Observable.just("muhaitian", "lizihai", "wangshaungshuang").concatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull String s) throws Exception {
                return Observable.just(s);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "onNext: " + s);
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
     * FlatMapIterable
     * <p>
     * 没找到相应的例子,不知道如何使用。
     */
    public static void FlatMapIterable() {
        Observable.just("wangkang", "lili", "wangjuan").flatMapIterable(new Function<String, Iterable<String>>() {
            @Override
            public Iterable<String> apply(@NonNull String s) throws Exception {
//                Iterable iterable =s;
                return null;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String o) {

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
     * SwitchMap
     * switchMap() 和 flatMap() 很像，除了一点：每当源Observable
     * 发射一个新的数据项（Observable）时，它将取消订阅并停止监视之前那个数据项
     * 产生的Observable，并开始监视当前发射的这一个。
     */
    public static void SwitchMap() {
        Observable.just("wangkang", "lili", "wangjuan").switchMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull String s) throws Exception {
                return null;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "SwitchMap onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "SwitchMap onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "SwitchMap onComplete: ");
            }
        });

    }

    /**
     * Scan
     * scan() 函数可以看做是一个累积函数。 scan() 函数对原始
     * Observable发射的每一项数据都应用一个函数，计算出函数的结果值，并将该值填
     * 充回可观测序列，等待和下一次发射的数据一起使用。累加操作
     */
    public static void Scan() {
        Observable.just(12, 23, 56, 89).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {

                return integer + integer2;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        //以575为第一发射与基础，进行累加操作
        Observable.just(12, 23, 56, 89).scan(575, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: " + integer);
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
     * GroupBy(名字上是分组，实际)
     */

    public static void GroupBy() {
        Observable<GroupedObservable<Integer, String>> groupItem =
                Observable.just("John", "Muhaitian", "Jose", "Mu").groupBy(new Function<String, Integer>() {

                    @Override
                    public Integer apply(@NonNull String s) throws Exception {
                        if (s.contains("M")) {
                            return 1;
                        } else if (s.contains("J")) {
                            return 2;
                        }
                        return 0;
                    }
                });

        groupItem.subscribe(new Observer<GroupedObservable<Integer, String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull GroupedObservable<Integer, String> integerStringGroupedObservable) {
                int key = integerStringGroupedObservable.getKey();
                switch (key) {
                    case 1:
                        integerStringGroupedObservable.subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull String s) {
                                Log.d(TAG, "onNext: key 1" + s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                        break;

                    case 2:
                        integerStringGroupedObservable.subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull String s) {
                                Log.d(TAG, "onNext: key 2" + s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                        break;
                }
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
     * buffer
     */
    public static void Buffer() {
        Log.d(TAG, "Buffer: buffer(int count)");
        Observable.just("mn", "mk", "mo", "mg").buffer(2).subscribe(new Observer<List<String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<String> strings) {
                Log.d(TAG, "onNext: strings =" + strings);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        Log.d(TAG, "Buffer: buffer(int count, int skip)");
        Observable.just("mn", "mk", "mo", "mg", "ml", "mh", "ma", "mr").buffer(1, 3).subscribe(new Observer<List<String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<String> strings) {
                Log.d(TAG, "onNext: buffer(1,2) " + strings);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Log.d(TAG, "Buffer: buffer(1, 3, new Callable<Collection<? super String>>()");
        Observable.just("mn", "mk", "mo", "mg", "ml", "mh", "ma", "mr").buffer(1, 3, new Callable<Collection<? super String>>() {
            @Override
            public Collection<? super String> call() throws Exception {
                Collection<String> mmm = new ArrayList<String>();
                mmm.add("kl");
                mmm.add("hj");
                return mmm;
            }
        }).subscribe(new Observer<Collection<? super String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Collection<? super String> objects) {
                Log.d(TAG, "onNext: " + objects);
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
     * Window
     * RxJava的 window() 函数和 buffer() 很像，但是它发射的是Observable而不是
     * 列表。下图展示了 window() 如何缓存3个数据项并把它们作为一个新的
     * Observable发射出去。
     */
    public static void Window() {
        Observable.just("mn", "mk", "mo", "mg", "ml", "mh", "ma", "mr", "mw").window(3).subscribe(new Observer<Observable<String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Observable<String> stringObservable) {
                Log.d(TAG, "stringObservable onNext: ");
                stringObservable.subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
     * cast
     * 自己写的例子，有错误，没搜索到例子，不知道错在哪了。
     */
    public static void cast() {
        Log.d(TAG, "cast: ");
        Observable.just("12", "23", "34", "56").cast(Integer.class).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: integer= " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    /**
     * Merge 合并数
     */
    public static void Merge() {
        Observable<String> observable = Observable.just("mk", "mn");
        Observable<String> observable1 = Observable.just("bh", "bn");
        Observable.merge(observable, observable1).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "onNext: " + s);
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
     * ZIP
     * <p>
     * 合并数据
     */
    public static void useZip() {
        Observable observable = Observable.just("jkjk", "ghkk", "ghh");
        Observable<Long> observable1 = Observable.interval(1, TimeUnit.SECONDS);
        Observable.zip(observable, observable1, new BiFunction<String, Long, String>() {
            @Override
            public String apply(@NonNull String s, @NonNull Long aLong) throws Exception {
                return s + "------" + aLong;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String o) {
                Log.d(TAG, "onNext: " + o);
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
     * join
     * 没找到如何使用
     */
    public static void useJoin() {
        Observable observable = Observable.interval(1, TimeUnit.SECONDS);

        observable.join(new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                observer.onNext(12L);
                observer.onNext(34L);
                observer.onNext(56L);
                observer.onNext(78L);
                observer.onNext(89L);
                observer.onNext(101L);
                observer.onComplete();
            }
        }, new Function<Long, Observable<String>>() {
            @Override
            public Observable<String> apply(@NonNull Long o) throws Exception {
                Log.d(TAG, "apply: " + o);
                return Observable.just(o + "--fun01");
            }
        }, new Function<Long, Observable<String>>() {
            @Override
            public Observable<String> apply(@NonNull Long o) throws Exception {
                return Observable.just(o + "--fun02");
            }
        }, new BiFunction<String, String, Observable<String>>() {
            @Override
            public Observable<String> apply(@NonNull String s, @NonNull String s2) throws Exception {
                Log.d(TAG, "apply: s" + s);
                Log.d(TAG, "apply: s2" + s2);
                return Observable.just(s + "------" + s2);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull String o) {
                Log.d(TAG, "onNext: " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    /**
     * combineLatest
     */
    public static void combineLatest() {

    }
    /**
     *
     * and then wen
     *
     * 没有相关的类，不知道需要引入什么样的库，暂时空缺。
     *
     */
    public static void AndThenWhen(){
        Observable<String> observable = Observable.just("12","23");
        Observable<Long> longObservable = Observable.interval(1,TimeUnit.SECONDS);
//
//        Pattern<String,Long> pattern = JoinObservable
    }

    /**
     *
     * switch
     *
     */
    public static void Switch(){

    }

    /**
     * StartWith
     *
     */
    public static void StartWith(){
        Observable observable = Observable.just("mk","mo","md");

        observable.startWith(new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                observer.onNext("hi");
                observer.onNext("hu");
                observer.onNext("hr");
                observer.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String o) {
                Log.d(TAG, "onNext: "+o);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
