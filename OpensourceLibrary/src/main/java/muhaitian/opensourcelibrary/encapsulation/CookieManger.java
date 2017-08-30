package muhaitian.opensourcelibrary.encapsulation;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import retrofit2.Converter;

/**
 * Created by muhaitian on 2017/8/21.
 */

public class CookieManger implements CookieJar {

    private static final String TAG = "CookieManger";
    private static Context mcontext;
//    private static Persis


    public CookieManger(Context context){
        mcontext = context;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return null;
    }
}
