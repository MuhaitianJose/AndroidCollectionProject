package muhaitian.opensourcelibrary.encapsulation;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by muhaitian on 2017/8/22.
 */

public class RetrofitClient {

    private static final String TAG = "RetrofitClient";

    private static final int DEFAULT_TIMEOUT = 20;
    private RetrofitApiService apiService;
    private static OkHttpClient okHttpClient;
    public static String baseUrl = RetrofitApiService.BASE_URL;
    private static Context mContext;
    private static RetrofitClient sNewInstance;

    private static Retrofit retrofit;
    private Cache cache = null;
    private File httpCacheFileDirectory;

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl);

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(mContext);
    }

    public static RetrofitClient getsInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    public static RetrofitClient getsInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }
        return new RetrofitClient(mContext, url);
    }

    public static RetrofitClient getsInstance(Context context, String url, Map<String, String> headers) {
        if (context != null) {
            mContext = context;
        }
        return new RetrofitClient(mContext, url, headers);
    }


    public RetrofitClient(Context context) {

    }

    public RetrofitClient(Context context, String url) {
        this(context, baseUrl, null);
    }

    public RetrofitClient(Context context, String url, Map<String, String> headers) {
        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }
        if (httpCacheFileDirectory == null) {
            httpCacheFileDirectory = new File(mContext.getCacheDir(), "muhaitian_cache");
        }
        try {
            if (cache == null) {
                cache = new Cache(httpCacheFileDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Log.e(TAG, "RetrofitClient: can not create http cache", e);
        }

        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(new CookieManger(context))
                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new CacheInterceptor(context))
                .addNetworkInterceptor(new CacheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS)).build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }
}
