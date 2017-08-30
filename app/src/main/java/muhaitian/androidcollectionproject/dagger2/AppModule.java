package muhaitian.androidcollectionproject.dagger2;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by muhaitian on 2017/8/29.
 */

@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context){
        mContext = context;
    }

    @Provides
    Context providerContext(){
        return mContext;
    }
}
