package muhaitian.androidcollectionproject.dagger2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by muhaitian on 2017/8/29.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface StudentForContext {
}
