package muhaitian.androidcollectionproject.rxjava.network;

import android.text.TextUtils;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.internal.http.RequestException;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by muhaitian on 2017/8/11.
 */

public abstract class BaseProtocol {
    /**
     * 创建一个返回工作在IO线程的被观察者的对象（被订阅者）
     *
     * @param url
     * @param method
     * @return
     */
    protected Observable<String> createObservable(final String url, final String method, final Map<String, Object> params) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                Request request = XgoHttpClient.getClient().getRequest(url, method, params);
                String results = XgoHttpClient.getClient().execute2String(request);
                setData(e, results);
            }
        }).subscribeOn(Schedulers.io());
    }

    protected void setData(@NonNull ObservableEmitter<String> observer, String Josn) {
        if (TextUtils.isEmpty(Josn)) {
            observer.onError(new Throwable("No data"));
            return;
        }
        observer.onNext(Josn);
        observer.onComplete();
    }
}
