package muhaitian.androidcollectionproject.rxjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
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
                return Observable.just("ObservableSource " + s);
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
        Observable<Long> observable1 = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {
                e.onNext(55555L);
                e.onComplete();
            }
        }).interval(1, TimeUnit.SECONDS);
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
     * and then wen
     * <p>
     * 没有相关的类，不知道需要引入什么样的库，暂时空缺。
     */
    public static void AndThenWhen() {
        Observable<String> observable = Observable.just("12", "23");
        Observable<Long> longObservable = Observable.interval(1, TimeUnit.SECONDS);
//
//        Pattern<String,Long> pattern = JoinObservable
    }

    /**
     * switch
     */
    public static void Switch() {

    }

    /**
     * StartWith
     */
    public static void StartWith() {
        Observable observable = Observable.just("mk", "mo", "md");

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
     * Do操作符就是给Observable的生命周期的各个阶段加上一系列的回调监听，当Observable执行到这个阶段的时候，这些回调就会被触发。
     * 在Rxjava实现了很多的doxxx操作符。DoOnEach可以给Observable加上这样的样一个回调：Observable每发射一个数据的时候就会触发这个回调，
     * 不仅包括onNext还包括onError和onCompleted。
     * <p>
     * doon
     */
    public void useDoon() {
        Observable.just("as", "bm", "jk").doOnEach(new Consumer<Notification<String>>() {
            @Override
            public void accept(Notification<String> stringNotification) throws Exception {
                Log.d(TAG, "accept: stringNotification==" + stringNotification);
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {

            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {

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
     * Meterialize操作符将OnNext/OnError/OnComplete都转化为一个Notification对象并按照原来的顺序发射出来
     * <p>
     * Meterialize
     */
    public void Meterialize() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("123");
                e.onNext("456");
                e.onNext("789");
                e.onNext("next");
                e.onNext("over");
                e.onComplete();
            }
        }).materialize().subscribe(new Observer<Notification<String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Notification<String> stringNotification) {
                Log.d(TAG, "onNext: stringNotification=" + stringNotification);
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
     * allObserver
     * <p>
     * all的用法不太清楚怎么处理
     */
    public static void allObserver() {
        Observable.just(1, 2, 3, 4, 5, 6, 7).all(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {

                if (integer >= 1) {
                    return true;
                }
                return false;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.d(TAG, "accept: aBoolean==" + aBoolean);
            }
        });
    }

    /**
     * Amb操作符可以将至多9个Observable结合起来，让他们竞争。哪个Observable首先发射了数据
     * （包括onError和onComplete)就会继续发射这个Observable的数据，其他的Observable所发射的数据都会别丢弃。
     */
    public static void ambObserver() {
        Observable<Integer> observable = Observable.just(1, 2, 3).delay(3000, TimeUnit.MILLISECONDS);
        Observable<Integer> observable1 = Observable.just(4, 5, 6).delay(2000, TimeUnit.MILLISECONDS);
        Observable<Integer> observable2 = Observable.just(7, 8, 9).delay(1000, TimeUnit.MILLISECONDS);
        Observable.ambArray(observable, observable1, observable2).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: integer=" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    /**
     * Contains、IsEmpty
     */
    public static void ContainsAndIsEmpty() {
        Log.d(TAG, "ContainsAndIsEmpty: contains:");
        Observable.just(12, 23, 34, 45, 56).contains(23).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.d(TAG, "accept: " + aBoolean);
            }
        });
        Log.d(TAG, "ContainsAndIsEmpty: IsEmpty:");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onComplete();
            }
        }).isEmpty().subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.d(TAG, "accept: " + aBoolean);
            }
        });

    }

    /**
     * DefaultIfEmpty操作符会判断源Observable是否发射数据，如果源Observable发射了数据则正常发射这些数据，如果没有则发射一个默认的数据
     * <p>
     * DefaultIfEmpty
     */
    public static void DefaultIfEmpty() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onComplete();
            }
        }).defaultIfEmpty("456").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "onNext: " + s);
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

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("wangkang");
                e.onComplete();
            }
        }).defaultIfEmpty("456").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "onNext: " + s);
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
     * SequenceEqual
     * <p>
     * SequenceEqual操作符用来判断两个Observable发射的数据序列是否相同（发射的数据相同，数据的序列相同，结束的状态相同），如果相同返回true，否则返回false
     */
    public static void SequenceEqual() {
        Observable<Integer> observable = Observable.just(12, 34, 56);
        Observable<Integer> observable1 = Observable.just(12, 34, 78);
        Observable.sequenceEqual(observable, observable1).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.d(TAG, "accept: aBoolean==" + aBoolean);
            }
        });
    }

    /**
     * SkipUntil、SkipWhile
     * 这两个操作符都是根据条件来跳过一些数据，不同之处在于SkipUnitl是根据一个标志Observable来判断的，当这个标志Observable没有发射数据的时候，
     * 所有源Observable发射的数据都会被跳过；当标志Observable发射了一个数据，则开始正常地发射数据。
     * <p>
     * SkipWhile则是根据一个函数来判断是否跳过数据，当函数返回值为true的时候则一直跳过源Observable发射的数据；当函数返回false的时候则开始正常发射数据。
     */
    public static void SkipUntilAndSkipWhile() {

        Observable.interval(1, TimeUnit.SECONDS).skipUntil(Observable.timer(3, TimeUnit.SECONDS)).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d(TAG, "skipUntil onNext: " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable.interval(1, TimeUnit.SECONDS).skipWhile(new Predicate<Long>() {
            @Override
            public boolean test(@NonNull Long aLong) throws Exception {
                return false;
            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d(TAG, "skipWhile onNext: " + aLong);
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
     * TakeUntil、TakeWhile
     * <p>
     * TakeUntil和TakeWhile操作符可以说和SkipUnitl和SkipWhile操作符是完全相反的功能。TakeUntil也是使用一个标志Observable是否发射数据来判断，
     * 当标志Observable没有发射数据时，正常发射数据，而一旦标志Observable发射过了数据则后面的数据都会被丢弃。
     */

    public static void TakeUntilAndTakeWhile() {
        Observable.interval(1, TimeUnit.SECONDS).takeUntil(Observable.timer(3, TimeUnit.SECONDS)).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d(TAG, "takeUntil onNext: " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable.interval(1, TimeUnit.SECONDS).takeWhile(new Predicate<Long>() {
            @Override
            public boolean test(@NonNull Long aLong) throws Exception {
                return true;
            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d(TAG, "takeWhile onNext: " + aLong);
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
     * Reduce、Collect
     * <p>
     * educe操作符应用一个函数接收Observable发射的数据和函数的计算结果作为下次计算的参数，输出最后的结果。
     * 跟前面我们了解过的scan操作符很类似，只是scan会输出每次计算的结果，而reduce只会输出最后的结果。
     * <p>
     * Collect操作符类似于Reduce，但是其目的不同，collect用来将源Observable发射的数据给收集到一个数据结构里面，需要使用两个参数：
     * 一个产生收集数据结构的函数。
     * 一个接收第一个函数产生的数据结构和源Observable发射的数据作为参数的函数。
     */
    public static void ReduceAndCollect() {
        Observable.just(12, 35, 78, 95).reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept: integer = " + integer);
            }
        });
        Observable.just(12, 34).collect(new Callable<List<Integer>>() {
            @Override
            public List<Integer> call() throws Exception {
                return new ArrayList<>();
            }
        }, new BiConsumer<List<Integer>, Integer>() {
            @Override
            public void accept(List<Integer> o, Integer integer) throws Exception {
                Log.d(TAG, "accept: List<Integer>=" + o);
                Log.d(TAG, "accept: Linteger=" + integer);
                o.add(integer);
            }
        }).subscribe(new BiConsumer<List<Integer>, Throwable>() {
            @Override
            public void accept(List<Integer> o, Throwable throwable) throws Exception {
                Log.d(TAG, "subscribe accept: " + o);
            }
        });
    }

}
