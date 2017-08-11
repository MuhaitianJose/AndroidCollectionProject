package muhaitian.rxjavaessentialsdemo.model;


import java.util.List;

import io.reactivex.Observable;

/**
 * Created by muhaitian on 2017/8/9.
 */

public class AppInfoInterface {

    public interface IAppInfoView{
        void reFreshListView(List<AppInfo> list);

    }

    public interface IAppIfoModel{
         Observable<AppInfo> getApps();
    }
}
