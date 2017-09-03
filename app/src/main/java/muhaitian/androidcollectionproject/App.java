package muhaitian.androidcollectionproject;

import android.app.Application;

import muhaitian.androidcollectionproject.dagger2.AppComponent;
import muhaitian.androidcollectionproject.dagger2.AppModule;
import muhaitian.androidcollectionproject.dagger2.DaggerAppComponent;

/**
 * Created by muhaitian on 2017/8/30.
 */

public class App extends Application{

    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

    }
}
