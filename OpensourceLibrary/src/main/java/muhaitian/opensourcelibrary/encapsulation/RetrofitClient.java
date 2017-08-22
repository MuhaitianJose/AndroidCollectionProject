package muhaitian.opensourcelibrary.encapsulation;

import android.content.Context;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by muhaitian on 2017/8/22.
 */

public class RetrofitClient {
    private static final int DEFAULT_TIMEOUT = 20;
    private RetrofitApiService apiService;
    private static OkHttpClient okHttpClient;
    public static String baseUrl = RetrofitApiService.BASE_URL;
    private static Context context;
    private static RetrofitClient sNewInstance;

    private static Retrofit retrofit;
    private Cache cache = null;
    private File httpCacheFileDirectory;

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl);
}
