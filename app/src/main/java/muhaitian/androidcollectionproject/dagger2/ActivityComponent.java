package muhaitian.androidcollectionproject.dagger2;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by muhaitian on 2017/8/29.
 */
@Singleton
@Component(dependencies = AppComponent.class ,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(Dagger2Activity activity);
}
