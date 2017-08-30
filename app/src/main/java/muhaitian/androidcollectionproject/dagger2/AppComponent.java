package muhaitian.androidcollectionproject.dagger2;

import android.content.Context;

import dagger.Component;

/**
 * Created by muhaitian on 2017/8/29.
 */
@PerApp
@Component(modules = AppModule.class)
public interface AppComponent {
    Context providerCon();
}
