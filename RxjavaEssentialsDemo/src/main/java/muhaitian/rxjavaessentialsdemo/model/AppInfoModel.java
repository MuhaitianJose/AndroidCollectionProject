package muhaitian.rxjavaessentialsdemo.model;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by muhaitian on 2017/8/9.
 */

public class AppInfoModel implements AppInfoInterface.IAppIfoModel{
    private Context context;

    public AppInfoModel(Context context) {
        this.context = context;
    }


    @Override
    public Observable<AppInfo> getApps() {
        return Observable.create(new ObservableOnSubscribe<AppInfo>() {
            @Override
            public void subscribe(ObservableEmitter<AppInfo> e) throws Exception {

            }
        });
    }
}
